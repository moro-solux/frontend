package com.solux.moro.di

import com.solux.moro.data.repository.ColorMapRepository
import com.solux.moro.data.repository.colormap.ColorMapRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ColorMapRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindColorMapRepository(
        colorMapRepositoryImpl: ColorMapRepositoryImpl
    ): ColorMapRepository
}