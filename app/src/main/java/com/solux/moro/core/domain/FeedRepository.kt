package com.solux.moro.core.domain

import com.solux.moro.data.model.CommentItem
import com.solux.moro.data.model.FeedItem
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    val refreshTrigger: Flow<Unit>
    fun triggerRefresh()
    fun getHomeFeed(): Flow<List<FeedItem>>

    fun getPosts(postId: Long): Flow<List<FeedItem>>

    suspend fun likeFeed(feedId: Long)

    suspend fun getLike(feedId: Long)

    suspend fun deleteFeed(feedId: Long)


    suspend fun getComment(feedId: Long): Flow<List<CommentItem>>

    suspend fun addComment(feedId: Long, content: String)


}