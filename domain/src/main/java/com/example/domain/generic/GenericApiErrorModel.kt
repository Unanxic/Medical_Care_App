package com.example.domain.generic

import com.google.gson.annotations.SerializedName

data class GenericApiErrorModel(
    @SerializedName("Message")
    val message: String? = null,
    @SerializedName("error")
    val error: String? = null,
    @SerializedName("error_description")
    val errorDescription: String? = null,
)
