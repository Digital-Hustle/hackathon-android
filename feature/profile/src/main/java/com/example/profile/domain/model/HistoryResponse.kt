package com.example.profile.domain.model


data class HistoryResponse(
    val fileId: Int,
    val fileName: String,
    val size: String,
    val timestamp: Long,
    val contentType: String
)
