package com.example.network.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.Buffer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class FileMapper(private val context: Context) {

    fun prepareFileForUpload(uri: Uri, extension: String): MultipartBody.Part {
        val file = getFileFromUri(context, uri, extension)
        val requestFile = file.asRequestBody("file/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    private fun getFileFromUri(context: Context, uri: Uri, extension: String): File {
        val destinationFile =
            File(context.cacheDir, "temp_file_${System.currentTimeMillis()}.$extension")
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return destinationFile
    }

    //    @SuppressLint("SupportAnnotationUsage")
//    @RequiresExtension(extension = "java.util.Base64", version = "1.8")
    fun saveMultipartToFile(fileName: String, multipartBody: MultipartBody.Part): File {
        // Создаем временный файл в кэш-директории
        val fileName = multipartBody.headers?.get("Content-Disposition")?.let {
            it.split("filename=").getOrNull(1)?.trim('"') ?: fileName
        } ?: "multipart_file_${System.currentTimeMillis()}"

        val destinationFile = File(context.cacheDir, fileName)

        val buffer = Buffer()
        multipartBody.body.writeTo(buffer)
        val bytes = buffer.readByteArray()

        FileOutputStream(destinationFile).use { outputStream ->
            outputStream.write(bytes)
        }

        return destinationFile
    }

    fun saveFileToInternalStorage(sourceFile: File, targetFileName: String): Boolean {
        return try {
            val targetFile = File(context.filesDir, targetFileName)

            FileInputStream(sourceFile).use { inputStream ->
                FileOutputStream(targetFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            true // Успешно
        } catch (e: IOException) {
            e.printStackTrace()
            false // Ошибка
        }
    }


    fun saveTemplate() {
        val uri = Uri.parse("android.resource://${context.packageName}/raw/my_excel_file")
        val file = getFileFromUri(context, uri, ".xlsx")
        saveFileToInternalStorage(file,"tns_template")
    }


}

