package com.solux.moro.data.model

data class CommentItem(
    val id: String,
    val userNickname: String,
    val content: String,
    val createdAt: Long
)