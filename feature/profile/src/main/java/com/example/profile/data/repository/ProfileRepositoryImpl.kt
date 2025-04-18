package com.example.profile.data.repository

import com.example.network.ApiResponse
import com.example.network.apiRequestFlow
import com.example.profile.data.network.ProfileApiService
import com.example.profile.domain.model.GetFileResponse
import com.example.profile.domain.model.GetHistoryResponse
import com.example.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val apiService: ProfileApiService) :
    ProfileRepository {
    override fun getHistory(page: Int, size: Int): Flow<ApiResponse<GetHistoryResponse>> =
        apiRequestFlow {
            apiService.getHistory(page, size)
        }

    override fun getFile(fileId: Int): Flow<ApiResponse<GetFileResponse>> = apiRequestFlow {
        apiService.getFile(fileId)
    }
}