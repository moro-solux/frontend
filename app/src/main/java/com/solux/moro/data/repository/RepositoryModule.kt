package com.solux.moro.data.repository

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

    @Binds
    abstract fun bindCommentRepository(
        impl: FakeCommentRepository
    ): CommentRepository

    @Binds
    abstract fun bindFeedRepository(
        impl: FakeFeedRepository
    ): FeedRepository

    @Binds
    abstract fun bindNotificationRepository(
        impl: FakeNotificationRepository
    ): NotificationRepository

    @Binds
    abstract fun bindFollowRepository(
        impl: FakeFollowRepository
    ): FollowRepository
}

