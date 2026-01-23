package com.solux.moro.core.domain

import com.solux.moro.data.model.CommentItem
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    fun observeComments(postId: Long): Flow<List<CommentItem>>

    suspend fun loadComments(
        postId: Long,
        cursor: String? = null
    )

    suspend fun addComment(
        postId: Long,
        content: String
    )

    //suspend fun deleteComment(commentId: Long)
}

