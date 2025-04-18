package com.example.send.domain.repository

import com.example.network.ApiResponse
import com.example.send.domain.model.UploadFileResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface SendRepository {

    fun uploadFile(
        file: MultipartBody.Part,
//        description: RequestBody
    ): Flow<ApiResponse<UploadFileResponse>>
}