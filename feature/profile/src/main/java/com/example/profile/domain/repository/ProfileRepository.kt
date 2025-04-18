package com.example.profile.domain.repository

import com.example.network.ApiResponse
import com.example.profile.domain.model.GetFileResponse
import com.example.profile.domain.model.GetHistoryResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getHistory(page: Int, size: Int): Flow<ApiResponse<GetHistoryResponse>>

    fun getFile(fileId: Int): Flow<ApiResponse<GetFileResponse>>
}