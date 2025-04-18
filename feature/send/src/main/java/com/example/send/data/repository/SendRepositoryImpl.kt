package com.example.send.data.repository

import com.example.network.ApiResponse
import com.example.network.apiRequestFlow
import com.example.send.data.network.SendApiService
import com.example.send.domain.model.UploadFileResponse
import com.example.send.domain.repository.SendRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class SendRepositoryImpl @Inject constructor(private val apiService: SendApiService) :
    SendRepository {
    override fun uploadFile(file: MultipartBody.Part): Flow<ApiResponse<UploadFileResponse>> =
        apiRequestFlow {
            apiService.uploadFile(file)
        }
}