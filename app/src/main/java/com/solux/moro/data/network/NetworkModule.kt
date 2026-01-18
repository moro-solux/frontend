package com.solux.moro.data.network

import com.google.gson.GsonBuilder
import com.solux.moro.data.service.ColorMapService
import com.solux.moro.data.service.SettingService
import com.solux.moro.data.service.UploadService
import com.solux.moro.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://moro-be.store/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val token=""

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
        val gson = GsonBuilder()
            .serializeNulls() //null인 필드도 JSON에 포함
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideSettingService(retrofit: Retrofit): SettingService {
        return retrofit.create(SettingService::class.java)
    }

    @Provides
    @Singleton
    fun provideUploadService(retrofit: Retrofit): UploadService {
        return retrofit.create(UploadService::class.java)
    }
    @Provides
    @Singleton
    fun provideColorMapService(retrofit: Retrofit): ColorMapService {
        return retrofit.create(ColorMapService::class.java)
    }
}