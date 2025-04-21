package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adnanrangrej.natureguardian.domain.model.chatbot.ChatBotRequest
import com.github.adnanrangrej.natureguardian.domain.model.chatbot.Message
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.usecase.chatbot.GetChatBotResponseUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.chatbot.InitializeChatBotUseCase
import com.github.adnanrangrej.natureguardian.domain.usecase.species.GetSpeciesByIdUseCase
import com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail.chatbot.ChatBotUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SpeciesDetailViewModel @Inject constructor(
    private val getSpeciesByIdUseCase: GetSpeciesByIdUseCase,
    private val initializeChatBotUseCase: InitializeChatBotUseCase,
    private val getChatBotResponseUseCase: GetChatBotResponseUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val internalTaxonId: Long = checkNotNull(savedStateHandle["internal_taxon_id"])
    val uiState: StateFlow<SpeciesDetailUiState> =
        getSpeciesByIdUseCase(internalTaxonId)
            .map {
                SpeciesDetailUiState.Success(it!!) as SpeciesDetailUiState
            }
            .catch { throwable ->
                emit(SpeciesDetailUiState.Error(throwable.message ?: "Unknown Error Occurred"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SpeciesDetailUiState.Loading
            )

    private val _chatBotUiState = mutableStateOf<ChatBotUiState>(ChatBotUiState.Loading)
    val chatBotUiState: State<ChatBotUiState> = _chatBotUiState
    private var isChatBotInitialized = false

    fun initializeChatBot() {
        if (uiState.value is SpeciesDetailUiState.Success && !isChatBotInitialized) {
            Log.d("SpeciesDetailViewModel", "Initializing chat bot")
            val species = (uiState.value as SpeciesDetailUiState.Success).species
            val systemInstruction = buildSystemInstruction(species)
            val initialPrompt = getInitialUserPrompt(species.species.scientificName)
            val userMessage = Message(role = "user", content = initialPrompt)
            _chatBotUiState.value = ChatBotUiState.Success(
                messages = listOf<Message>(userMessage),
                isLoading = true,
                isError = false,
                errorMessage = null
            )
            val currentState = _chatBotUiState.value as ChatBotUiState.Success
            viewModelScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        initializeChatBotUseCase(
                            initialPrompt = initialPrompt,
                            sys = systemInstruction
                        )
                    }
                    if (response.isError) {
                        throw Exception(response.errorMessage)
                    }
                    Log.d("SpeciesDetailViewModel", "Response received: $response")
                    val modelMessage = Message(
                        role = "model",
                        content = response.responseText ?: "Error generating response"
                    )
                    _chatBotUiState.value = currentState.copy(
                        messages = currentState.messages + modelMessage,
                        isLoading = false,
                        isError = false,
                        errorMessage = null
                    )
                    isChatBotInitialized = true
                } catch (e: Exception) {
                    Log.e("SpeciesDetailViewModel", "Error initializing chat bot", e)
                    _chatBotUiState.value = ChatBotUiState.Error("Error initializing chat bot")
                }
            }
        }
    }

    fun getChatBotResponse(prompt: String) {
        val speciesSuccessState = uiState.value as? SpeciesDetailUiState.Success ?: return
        val species = speciesSuccessState.species
        if (!isChatBotInitialized) {
            initializeChatBot()
            Log.w(
                "SpeciesDetailViewModel",
                "Chatbot not initialized. Initializing now. User may need to resend message."
            )
            return
        }

        val currentChatSuccessState = _chatBotUiState.value as? ChatBotUiState.Success
        if (currentChatSuccessState == null) {
            Log.e(
                "SpeciesDetailViewModel",
                "getChatBotResponse called while chat state is not Success."
            )
            _chatBotUiState.value =
                ChatBotUiState.Error("Cannot send message, chat not ready.")
            return
        }
        Log.d("SpeciesDetailViewModel", "Getting chat bot response for prompt: $prompt")
        val systemInstruction = buildSystemInstruction(species)
        val userMessage = Message(role = "user", content = prompt)
        val stateAfterUserMessageAdded = currentChatSuccessState.copy(
            messages = currentChatSuccessState.messages + userMessage,
            isLoading = true,
            isError = false,
            errorMessage = null
        )
        _chatBotUiState.value = stateAfterUserMessageAdded

        viewModelScope.launch {
            try {
                val historyForRequest = stateAfterUserMessageAdded.messages.dropLast(1)
                val request = ChatBotRequest(
                    history = historyForRequest,
                    prompt = prompt,
                    systemInstruction = systemInstruction
                )

                Log.d("SpeciesDetailViewModel", "Sending request to UseCase: $request")
                val response = withContext(Dispatchers.IO) {
                    getChatBotResponseUseCase(request = request)
                }
                Log.d("SpeciesDetailViewModel", "Response received from UseCase: $response")

                if (response.isError) {
                    throw Exception(response.errorMessage ?: "Chat service returned an error")
                }

                val modelMessage = Message(
                    role = "model",
                    content = response.responseText?.takeIf { it.isNotBlank() }
                        ?: "(No text content received)"
                )
                _chatBotUiState.value = stateAfterUserMessageAdded.copy(
                    messages = stateAfterUserMessageAdded.messages + modelMessage,
                    isLoading = false
                )
                Log.d("SpeciesDetailViewModel", "Chat state updated with model response.")

            } catch (e: Exception) {
                Log.e("SpeciesDetailViewModel", "Error getting chat bot response", e)
                _chatBotUiState.value = stateAfterUserMessageAdded.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Failed to get response"
                )
            }
        }
    }


    fun regenerateResponse() {
        val speciesSuccessState = uiState.value as? SpeciesDetailUiState.Success ?: return
        val currentChatSuccessState = _chatBotUiState.value as? ChatBotUiState.Success ?: return
        if (!isChatBotInitialized) {
            Log.w("SpeciesDetailViewModel", "Cannot regenerate, chatbot not initialized.")
            return
        }
        val lastUserMessage = currentChatSuccessState.messages.findLast { it.role == "user" }
        if (lastUserMessage == null) {
            Log.w("SpeciesDetailViewModel", "Cannot regenerate, no user message found in history.")
            return
        }
        val systemInstruction = buildSystemInstruction(speciesSuccessState.species)
        val stateBeforeRegeneration = currentChatSuccessState.copy(
            isLoading = true,
            isError = false,
            errorMessage = null
        )
        _chatBotUiState.value = stateBeforeRegeneration
        viewModelScope.launch {
            try {
                val historyForRequest =
                    stateBeforeRegeneration.messages.takeWhile { it != lastUserMessage }

                val request = ChatBotRequest(
                    history = historyForRequest,
                    prompt = lastUserMessage.content,
                    systemInstruction = systemInstruction
                )

                Log.d("SpeciesDetailViewModel", "Sending regeneration request: $request")
                val response = withContext(Dispatchers.IO) {
                    getChatBotResponseUseCase(request)
                }
                Log.d("SpeciesDetailViewModel", "Regeneration response received: $response")

                if (response.isError) {
                    throw Exception(
                        response.errorMessage
                            ?: "Chat service returned an error during regeneration"
                    )
                }
                val newModelMessage = Message(
                    role = "model",
                    content = response.responseText?.takeIf { it.isNotBlank() }
                        ?: "(No text content received)"
                )
                val updatedMessages =
                    if (stateBeforeRegeneration.messages.lastOrNull()?.role == "model") {
                        stateBeforeRegeneration.messages.dropLast(1) + newModelMessage
                    } else {
                        stateBeforeRegeneration.messages + newModelMessage
                    }
                _chatBotUiState.value = stateBeforeRegeneration.copy(
                    messages = updatedMessages,
                    isLoading = false
                )
                Log.d("SpeciesDetailViewModel", "Chat state updated after regeneration.")

            } catch (e: Exception) {
                Log.e("SpeciesDetailViewModel", "Error regenerating chat bot response", e)
                _chatBotUiState.value = stateBeforeRegeneration.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Failed to regenerate response"
                )
            }
        }
    }


    fun getCommonName(detailedSpecies: DetailedSpecies): String? {
        val mainName = detailedSpecies.commonNames.find {
            it.isMain == true
        }
        return mainName?.commonName
    }

    fun getMainUrl(detailedSpecies: DetailedSpecies): String? {
        return detailedSpecies.images.firstOrNull()?.imageUrl
    }

    private fun buildSystemInstruction(species: DetailedSpecies): String {
        // Extract core information
        val scientificName = species.species.scientificName
        val status = species.species.redlistCategory
        val redListCriteria = species.species.redlistCriteria

        val habitatText = species.habitats.joinToString { it.habitatName }
        val threatsText = species.threats.joinToString { it.threatName }

        val basicInfo = buildString {
            appendLine("Species Scientific Name: $scientificName")

            status.takeIf { it.isNotBlank() }?.let { appendLine("Conservation Status: $it") }
            redListCriteria.takeIf { it.isNotBlank() }?.let { appendLine("Red-list Criteria: $it") }
            habitatText.takeIf { it.isNotBlank() }?.let { appendLine("Known Habitats: $it") }
            threatsText.takeIf { it.isNotBlank() }?.let { appendLine("Major Threats: $it") }
        }.trimEnd()

        return """
    You are an expert conservation assistant AI, knowledgeable about global biodiversity, conservation statuses (like IUCN Red List), habitats, and threats.

    The user is currently viewing information about the species: "$scientificName".

    Here is some contextual information provided from our data:
    $basicInfo

    **Your Task:**
    1.  **Prioritize Provided Info:** Treat the information above as the primary source of truth if relevant to the user's query.
    2.  **Expand Knowledge:** Use your broader biodiversity knowledge to provide more details, context, or answer questions, especially if the provided information is incomplete (e.g., missing habitats, threats, or conservation details).
    3.  **Focus:** Keep all responses strictly focused on "$scientificName". Discuss its characteristics, habitat, diet, behavior, conservation status, threats, ongoing conservation efforts, interesting facts, etc.
    4.  **Clarity:** Present information clearly. Use bullet points or short paragraphs where appropriate.
    5.  **Tone:** Be informative, helpful, engaging, and accessible.
    6.  **Handle Off-Topic:** If the user asks questions unrelated to this specific species, politely guide them back to discussing "$scientificName". Do not answer unrelated questions.
    7.  **Accuracy:** Avoid speculation. If information isn't available in the provided context or your knowledge base, state that clearly.

    Begin the conversation by offering a brief, interesting overview or asking the user what they'd like to know about "$scientificName".
    """.trimIndent()
    }

    private fun getInitialUserPrompt(speciesName: String): String {
        val prompts = listOf(
            "Hi! I'm curious about $speciesName. Can you tell me something interesting?",
            "Hey! What’s special about $speciesName?",
            "Can you give me a cool fact about $speciesName?",
            "I want to learn about $speciesName — where does it live and what are its threats?",
            "Tell me something unique about $speciesName.",
            "What’s the conservation status of $speciesName? Is it endangered?"
        )
        return prompts.random()
    }
}

