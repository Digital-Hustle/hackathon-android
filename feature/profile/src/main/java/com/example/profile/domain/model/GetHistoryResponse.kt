package com.example.profile.domain.model

import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(
    @SerializedName("data")
    val data: List<HistoryResponse>,
    @SerializedName("message")
    val message: String
)
