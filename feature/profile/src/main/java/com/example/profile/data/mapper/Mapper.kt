package com.example.profile.data.mapper

import com.example.profile.domain.model.HistoryResponse
import com.example.profile.presentation.model.FileType
import com.example.profile.presentation.model.HistoryReport
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun HistoryResponse.toReport() = HistoryReport(
    fileId = fileId,
    fileName = fileName,
    size = size,
    timestamp = formatUnixTime(timestamp),
    contentType = FileType.getByExtension(contentType)
)

fun List<HistoryResponse>.toReports(): List<HistoryReport> {
    return this.map { it.toReport() }
}

fun formatUnixTime(unixTimeMillis: Long): String {
    val dateFormat = SimpleDateFormat("HH:mm-MM.dd.yyyy", Locale.getDefault())
    return dateFormat.format(Date(unixTimeMillis))
}