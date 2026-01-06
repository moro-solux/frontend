package com.solux.moro.ui.profile

import com.solux.moro.ui.auth.AuthRepository
import com.solux.moro.ui.auth.component.FakeAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        impl: FakeUserRepository
    ): UserRepository

    @Binds
    abstract fun bindAuthRepository(
        impl: FakeAuthRepository
    ): AuthRepository
}
