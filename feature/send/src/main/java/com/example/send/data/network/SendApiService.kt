package com.example.send.data.network

import com.example.send.domain.model.UploadFileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Part

interface SendApiService {

    @POST("/api/v1/auth/login")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
    ): Response<UploadFileResponse>



}