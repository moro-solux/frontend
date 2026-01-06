package com.solux.moro.repository

import com.solux.moro.ui.auth.AuthRepository
import com.solux.moro.ui.auth.component.FakeAuthRepository
import com.solux.moro.ui.home.CommentRepository
import com.solux.moro.ui.home.FakeCommentRepository
import com.solux.moro.ui.home.FakeFeedRepository
import com.solux.moro.ui.home.FeedRepository
import com.solux.moro.ui.profile.FakeUserRepository
import com.solux.moro.ui.profile.UserRepository
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

    @Binds
    abstract fun bindCommentRepository(
        impl: FakeCommentRepository
    ): CommentRepository

    @Binds
    abstract fun bindFeedRepository(
        impl: FakeFeedRepository
    ): FeedRepository
}

