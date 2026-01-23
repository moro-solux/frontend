package com.solux.moro.data.repository

import android.util.Log
import com.solux.moro.core.domain.CommentRepository
import com.solux.moro.data.mapper.toDomain
import com.solux.moro.data.model.CommentItem
import com.solux.moro.data.service.FeedService
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class CommentImpl  @Inject constructor(
    private val feedService: FeedService
): CommentRepository{

    private val _commentStore = MutableStateFlow<Map<Long, List<CommentItem>>>(emptyMap())

    override fun observeComments(postId: Long): Flow<List<CommentItem>> {
        return _commentStore.map { it[postId] ?: emptyList() }
    }
    override suspend fun loadComments(postId: Long, cursor: String?) {
        try {
            val response = feedService.getComments(postId)
            if (response.success) {
                val commentList = response.data?.map { it.toDomain() } ?: emptyList()
                _commentStore.update { currentMap ->
                    currentMap + (postId to commentList)
                }
            }
        } catch (e: Exception) {
            Log.e("FeedRepository", "댓글 로딩 중 에러 발생: ${e.message}")

        }
    }

    override suspend fun addComment(postId: Long, content: String) {
        try {
            val response = feedService.addComment(postId, content)
            if (response.success) {
                loadComments(postId, null)
            }
        } catch (e: Exception) {
            Log.e("FeedRepository", "댓글 추가 실패: ${e.message}")
        }
    }
}