package com.solux.moro.data.repository

import com.solux.moro.data.model.CommentItem
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCommentRepository @Inject constructor(): CommentRepository {
    private val _comments = MutableStateFlow<List<CommentItem>>(emptyList())

    override fun observeComments(postId: Long): Flow<List<CommentItem>> = _comments

    override suspend fun loadComments(postId: Long, cursor: String?) {
        _comments.value = listOf(
            CommentItem(1, "@user1", "댓글 화면 테스트1", System.currentTimeMillis()),
            CommentItem(2, "@user2", "댓글 화면 테스트2", System.currentTimeMillis())
        )
    }

    override suspend fun addComment(postId: Long, content: String) {
        val newComment = CommentItem(
            id = System.currentTimeMillis(),
            userNickname = "@me",
            content = content,
            createdAt = System.currentTimeMillis()
        )
        _comments.value = _comments.value + newComment
    }

    override suspend fun deleteComment(commentId: Long) {
        _comments.value = _comments.value.filter { it.id != commentId }
    }
}