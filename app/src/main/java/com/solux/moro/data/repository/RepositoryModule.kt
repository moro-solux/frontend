package com.solux.moro.data.repository

import com.solux.moro.core.domain.CommentRepository
import com.solux.moro.core.domain.FeedRepository
import com.solux.moro.core.domain.FollowRepository
import com.solux.moro.core.domain.NotificationRepository
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.test.repository.FakeCommentRepository
import com.solux.moro.test.repository.FakeFeedRepository
import com.solux.moro.test.repository.FakeFollowRepository
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
        impl: UserRepositoryImpl
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
        impl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    abstract fun bindFollowRepository(
        impl: FakeFollowRepository
    ): FollowRepository


}

