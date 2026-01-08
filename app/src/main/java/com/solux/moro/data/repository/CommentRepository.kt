package com.solux.moro.data.repository

import com.solux.moro.data.model.CommentItem
import kotlinx.coroutines.flow.Flow

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

