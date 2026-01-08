package com.solux.moro.data.repository

import com.solux.moro.core.designsystem.theme.MoroPalette
import com.solux.moro.data.model.FeedItem
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeFeedRepository @Inject constructor(): FeedRepository {
    private val _homeFeed = MutableStateFlow(
        listOf(
            FeedItem(
                id = "1",
                authorId = "user1",
                authorNickname = "테스트유저",
                authorProfileColor = MoroPalette.Pastel.Cyan200,
                hexCodes = listOf(
                    "#3357FF",
                    "#4537AF",
                    "#7587BF",
                    "#0327CF",
                ),
                contentColors = listOf(
                    MoroPalette.Pastel.Purple400,
                    MoroPalette.Pastel.Orange300,
                    MoroPalette.Pastel.Cyan400,
                    MoroPalette.Pastel.Gray400,
                ),
                imageUrl = null,
                commentCount = 10,
                likeCount = 12,
                isLiked = false,
                createdAt = System.currentTimeMillis()
            )
        )
    )

    override fun getHomeFeed(): Flow<List<FeedItem>> = _homeFeed

    override fun getUserFeed(userId: String): Flow<List<FeedItem>> =
        _homeFeed.map { list -> list.filter { it.authorId == userId } }

    override suspend fun refreshHomeFeed() {
        //
    }

    override suspend fun likeFeed(feedId: String) {
        _homeFeed.update { list ->
            list.map {
                if (it.id == feedId)
                    it.copy(
                        isLiked = true,
                        likeCount = it.likeCount + 1
                    )
                else it
            }
        }
    }

    override suspend fun unlikeFeed(feedId: String) {
        _homeFeed.update { list ->
            list.map {
                if (it.id == feedId)
                    it.copy(
                        isLiked = false,
                        likeCount = it.likeCount - 1
                    )
                else it
            }
        }
    }
}