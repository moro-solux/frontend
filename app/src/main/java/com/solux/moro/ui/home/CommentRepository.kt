package com.solux.moro.ui.home

import kotlinx.coroutines.flow.Flow

data class CommentItem(
    val id: String,
    val userNickname: String,
    val content: String,
    val createdAt: Long
)

interface CommentRepository {

    fun observeComments(postId: String): Flow<List<CommentItem>>

    suspend fun loadComments(
        postId: String,
        cursor: String? = null
    )

    suspend fun addComment(
        postId: String,
        content: String
    )

    suspend fun deleteComment(commentId: String)
}

