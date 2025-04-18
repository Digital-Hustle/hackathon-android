package com.example.send.domain.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part

data class UploadFileResponse(
    @SerializedName("fileName")
    val fileName: String,
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("size")
    val size: String,
    @Part("file")
    val file: MultipartBody.Part
)
