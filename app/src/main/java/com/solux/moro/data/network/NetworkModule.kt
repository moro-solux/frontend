package com.solux.moro.data.network

import com.google.gson.GsonBuilder
import com.solux.moro.data.service.AuthService
import com.solux.moro.data.service.ColorMapService
import com.solux.moro.data.service.FeedService
import com.solux.moro.data.service.FollowService
import com.solux.moro.data.service.MapService
import com.solux.moro.data.service.NotificationService
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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://moro-be.store"
    var token = ""
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val authToken = token.trim()
                val requestBuilder = chain.request().newBuilder()
                if (authToken.isNotBlank()) {
                    requestBuilder.addHeader("Authorization", "Bearer $authToken")
                }
                chain.proceed(requestBuilder.build())
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
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationService(retrofit: Retrofit): NotificationService {
        return retrofit.create(NotificationService::class.java)
    }

    @Provides
    @Singleton
    fun provideFollowService(retrofit: Retrofit): FollowService {
        return retrofit.create(FollowService::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedService(retrofit: Retrofit): FeedService {
        return retrofit.create(FeedService::class.java)
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

    @Provides
    @Singleton
    fun provideMapService(retrofit: Retrofit): MapService {
        return retrofit.create(MapService::class.java)
    }
}
