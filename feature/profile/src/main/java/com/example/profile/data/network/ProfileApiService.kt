package com.example.profile.data.network

import com.example.profile.domain.model.GetFileResponse
import com.example.profile.domain.model.GetHistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApiService {
//    @POST("/api/v1/uploadFile/")
//    suspend fun login(
////        @Body loginRequest: LoginRequest,
//    )
////    : Response<LoginResponse>


    @GET("/api/v1/getHistory")
    suspend fun getHistory(
        @Query("page") page: Int,
        @Query("size") size: Int
//        @Body registrationRequest: RegistrationRequest
    ):Response<GetHistoryResponse>
//    : Response<RegistrationResponse>

    @GET("/api/v1/getFILE")
    suspend fun getFile(
        @Path("fileId") fileId: Int
//        @Body googleAuthRequest: GoogleAuthRequest
    ):Response<GetFileResponse>
//    : Response<LoginResponse>
}