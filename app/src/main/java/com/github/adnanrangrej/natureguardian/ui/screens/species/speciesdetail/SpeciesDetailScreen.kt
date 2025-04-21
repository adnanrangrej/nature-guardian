package com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.adnanrangrej.natureguardian.ui.screens.species.speciesdetail.chatbot.ChatWindow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeciesDetailScreen(
    viewModel: SpeciesDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    var isChatOpen by rememberSaveable { mutableStateOf(false) }
    val chatBotUiState by viewModel.chatBotUiState
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SpeciesDetailBody(
            modifier = Modifier
                .fillMaxSize(),
            uiState = uiState.value,
            getCommonName = viewModel::getCommonName,
            getImageUrl = viewModel::getMainUrl
        )
        FloatingActionButton(
            onClick = onNavigateUp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp)
                .clip(CircleShape)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        AnimatedVisibility(
            visible = !isChatOpen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    isChatOpen = true
                    viewModel.initializeChatBot()
                }
            ) {
                Icon(imageVector = Icons.Filled.AutoAwesome, contentDescription = "Chat")
            }
        }
        AnimatedVisibility(
            visible = isChatOpen,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(end = 16.dp, bottom = 16.dp, start = 16.dp)
        ) {
            ChatWindow(
                uiState = chatBotUiState,
                onMessageSent = viewModel::getChatBotResponse,
                onClose = { isChatOpen = false },
                onRetry = viewModel::regenerateResponse
            )
        }
    }
}