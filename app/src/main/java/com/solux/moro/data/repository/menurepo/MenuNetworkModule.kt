package com.solux.moro.data.repository.menurepo

import com.solux.moro.data.service.SettingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
        return Retrofit.Builder()
            .baseUrl("http://moro-be.store") // Swagger 서버 주소
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSettingService(@Named("MenuRetrofit") retrofit: Retrofit): SettingService {
        return retrofit.create(SettingService::class.java)
    }
}