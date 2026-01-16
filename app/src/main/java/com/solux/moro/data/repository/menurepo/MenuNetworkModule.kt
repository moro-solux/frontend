package com.solux.moro.data.repository.menurepo

import com.solux.moro.data.service.SettingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MenuNetworkModule {

    @Provides
    @Singleton
    @Named("MenuRetrofit")
    fun provideMenuRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://moro-be.store") // Swagger 주소
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideSettingService(@Named("MenuRetrofit") retrofit: Retrofit): SettingService {
        return retrofit.create(SettingService::class.java)
    }
}