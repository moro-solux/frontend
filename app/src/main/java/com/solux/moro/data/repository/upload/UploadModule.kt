package com.solux.moro.data.repository.upload

import com.solux.moro.data.repository.UploadRepository
import com.solux.moro.data.service.UploadService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

// Repository 연결
@Module
@InstallIn(SingletonComponent::class)
abstract class UploadRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUploadRepository(
        uploadRepositoryImpl: UploadRepositoryImpl
    ): UploadRepository
}

