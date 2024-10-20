package com.mertg.inst2convert.di

import com.mertg.inst2convert.service.InstApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://charm-chemical-banjo.glitch.me/") // Base URL'in
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideInstApiService(retrofit: Retrofit): InstApiService {
        return retrofit.create(InstApiService::class.java)
    }
}
