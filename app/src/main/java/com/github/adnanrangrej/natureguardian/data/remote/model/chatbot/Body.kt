package com.github.adnanrangrej.natureguardian.data.remote.model.chatbot


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("error")
    val error: String?,
    @SerializedName("is_error")
    val isError: Boolean,
    @SerializedName("response")
    val response: Response?
)