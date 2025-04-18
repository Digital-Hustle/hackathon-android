package com.example.profile.presentation.model


data class HistoryReport(
    val fileId: Int,
    val fileName: String,
    val size: String,
    val timestamp: String,
    val contentType: FileType
)

enum class FileType(val extension: String) {
    XLS(".xls"), XLSX(".xlsx");

    companion object {
        fun getByExtension(extension: String): FileType {
            return entries.firstOrNull { it.extension.equals(extension, ignoreCase = true) }
                ?: XLS
        }
    }
}