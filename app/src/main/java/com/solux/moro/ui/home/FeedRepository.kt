package com.solux.moro.ui.home

import kotlinx.coroutines.flow.Flow

data class FeedItem(
    val id: String,
    val authorId: String,
    val authorNickname: String,
    val authorProfileColor: androidx.compose.ui.graphics.Color,
    val hexCodes: List<String>,
    val contentColors: List<androidx.compose.ui.graphics.Color>,
    val imageUrl: String?,
    val commentCount: Int,
    val likeCount: Int,
    val isLiked: Boolean,
    val createdAt: Long
)

interface FeedRepository {

    fun getHomeFeed(): Flow<List<FeedItem>>

    fun getUserFeed(userId: String): Flow<List<FeedItem>>

    suspend fun refreshHomeFeed()

    suspend fun likeFeed(feedId: String)

    suspend fun unlikeFeed(feedId: String)
}