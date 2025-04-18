package com.example.send.presentation.di

import com.example.send.data.network.SendApiService
import com.example.send.data.repository.SendRepositoryImpl
import com.example.send.domain.repository.SendRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {
    @Singleton
    @Provides
    fun provideSendAPIService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): SendApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(SendApiService::class.java)

    @Singleton
    @Provides
    fun provideSendRepository(
        apiService: SendApiService,
    ): SendRepository = SendRepositoryImpl(apiService)
}