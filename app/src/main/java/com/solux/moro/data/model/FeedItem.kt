package com.solux.moro.data.model

import androidx.compose.ui.graphics.Color

data class FeedItem(
    val id: String,
    val authorId: String,
    val authorNickname: String,
    val authorProfileColor: Color,
    val hexCodes: List<String>,
    val contentColors: List<Color>,
    val imageUrl: String?,
    val commentCount: Int,
    val likeCount: Int,
    val isLiked: Boolean,
    val createdAt: Long
)