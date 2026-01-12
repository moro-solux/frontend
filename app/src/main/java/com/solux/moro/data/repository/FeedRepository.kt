package com.solux.moro.data.repository

import com.solux.moro.data.model.FeedItem
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getHomeFeed(): Flow<List<FeedItem>>

    fun getUserFeed(userId: Long): Flow<List<FeedItem>>

    suspend fun refreshHomeFeed()

    suspend fun likeFeed(feedId: Long)

    suspend fun unlikeFeed(feedId: Long)
}