package com.solux.moro.data.repository

import android.util.Log
import com.solux.moro.core.domain.FeedRepository
import com.solux.moro.data.mapper.toDomain
import com.solux.moro.data.model.CommentItem
import com.solux.moro.data.model.CommentRequest
import com.solux.moro.data.model.FeedItem
import com.solux.moro.data.service.FeedService
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FeedRepositoryImpl@Inject constructor(
    private val feedService: FeedService
): FeedRepository {

    override val refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply { tryEmit(Unit) }

    override fun triggerRefresh() {
        refreshTrigger.tryEmit(Unit)
    }


    override fun getHomeFeed(): Flow<List<FeedItem>> = flow {
        val response = feedService.getFeed()
        if (response.success) {
            val feedData = response.data
            val items = feedData.content.map { it.toDomain() }
            emit(items)
        } else {
            emit(emptyList())
        }
    }
    override fun getPosts(postId: Long): Flow<List<FeedItem>> = flow {
        val response = feedService.getPost(postId)
        if (response.success) {
            val postDto = response.data
            if (postDto != null) {
                emit(listOf(postDto.toDomain()))
            } else {
                emit(emptyList())
            }
        } else {
            emit(emptyList())
        }
    }.catch { e ->
        Log.e("FeedRepository", "getPosts 에러: ${e.message}")
        emit(emptyList())
    }


    override suspend fun likeFeed(feedId: Long) {
        try {
            val response = feedService.likeFeed(feedId)
            if (response.success) {
                Log.d("FeedRepository", "좋아요 성공")
            }
        } catch (e: Exception) {
            Log.e("FeedRepository", "좋아요 실패: ${e.message}")
        }
    }

    override suspend fun getLike(feedId: Long){
        try {
            val response = feedService.getLike(feedId)
            if (response.success) {
                Log.d("FeedRepository", "좋아요 조회 성공: 현재 총 좋아요 수 ${response.data.totalCount}")
            }
        } catch (e: Exception) {
            Log.e("FeedRepository", "좋아요 조회 실패: ${e.message}")
        }
    }
    override suspend fun deleteFeed(feedId: Long) {
        try {
            val response = feedService.deletePost(feedId)
            if (response.success) {
                Log.d("FeedRepository", "게시물 삭제 성공")
            }
        } catch (e: Exception) {
            Log.e("FeedRepository", "삭제 실패: ${e.message}")
        }
    }

    override suspend fun getComment(feedId: Long): Flow<List<CommentItem>> = flow<List<CommentItem>> {
        val response = feedService.getComments(feedId)
        if (response.success) {
            val commentDtos = response.data
            val items = commentDtos.map { it.toDomain() }
            emit(items)
        } else {
            emit(emptyList())
        }
    }.catch { e ->
        Log.e("FeedRepository", "댓글 로딩 중 에러 발생: ${e.message}")
        emit(emptyList())
    }

    override suspend fun addComment(feedId: Long, content: String) {
        try {
            val response = feedService.addComment(feedId, CommentRequest(content))
            if (response.success) {
                Log.d("FeedRepository", "댓글 추가 성공")
            }
        } catch (e: Exception) {
            Log.e("FeedRepository", "댓글 추가 실패: ${e.message}")
        }
    }
}