package com.solux.moro.data.repository

import androidx.compose.ui.graphics.Color
import com.solux.moro.core.designsystem.theme.MoroPalette
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.data.dto.UserSearchBaseResponse
import com.solux.moro.data.dto.UserSearchDto
import com.solux.moro.data.dto.UserSearchResponseDto
import com.solux.moro.data.model.FeedItem
import com.solux.moro.data.model.User
import com.solux.moro.data.model.UserColorPalette
import com.solux.moro.data.model.UserStats
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class FakeUserRepository @Inject constructor() : UserRepository {

    private val _user = MutableStateFlow<User?>(
        User(
            id = 1,
            email = "test@test.com",
            nickname = "테스트유저",
            visible = false,
            userColorHex="#tset12",
            colorPalette = UserColorPalette(
                theme = MoroThemeType.Pastel,
                userColor = MoroPalette.Pastel.Purple400,
                paletteColors = listOf(
                    MoroPalette.Pastel.Purple400,
                    MoroPalette.Pastel.Yellow300,
                    MoroPalette.Pastel.Green200,
                    MoroPalette.Pastel.Cyan200,
                    MoroPalette.Pastel.Indigo500,
                    MoroPalette.Pastel.Gray400
                )
                /*
                listOf(
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent)
                 */
            )
        )
    )
    override suspend fun searchUsers(
        query: String,
        page: Int
    ): Result<UserSearchBaseResponse<UserSearchResponseDto>> {
        // 테스트용 가짜 응답 데이터 생성
        val fakeResponse = UserSearchBaseResponse(
            success = true,
            status = 200,
            message = "성공",
            data = UserSearchResponseDto(
                content = listOf(UserSearchDto(userId = 1, userName = "가짜모로")),
                currentPage = 0,
                totalPages = 1,
                hasNext = false
            )
        )
        return Result.success(fakeResponse)
    }
    override val user: StateFlow<User?> = _user.asStateFlow()
    private val _userStats = MutableStateFlow<UserStats?>(null)
    override val userStats: StateFlow<UserStats?> = _userStats

    override suspend fun getUserTheme(): MoroThemeType {
        return _user.value?.colorPalette?.theme ?: MoroThemeType.Pastel
    }

    override suspend fun loadUser() {

    }

    override suspend fun updateUserColorPalette(
        palette: UserColorPalette
    ) {
        _user.update { it?.copy(colorPalette = palette) }
    }

    override suspend fun updateNickname(nickname: String) {
        _user.update { user ->
            user?.copy(nickname = nickname)
        }
    }
    override fun getUserPosts(userId: String): Flow<List<FeedItem>> = flow {
        val fakePosts = listOf(
            FeedItem(
                id = "post_1",
                authorId = userId,
                authorNickname = "ColorHunter",
                authorProfileColor = Color.Companion.Magenta,
                hexCodes = listOf("#FF5733", "#33FF57"),
                contentColors = listOf(Color(0xFFFF5733), Color(0xFF33FF57)),
                imageUrl = null,
                commentCount = 5,
                likeCount = 12,
                isLiked = true,
                createdAt = System.currentTimeMillis()
            ),
            FeedItem(
                id = "post_1",
                authorId = userId,
                authorNickname = "ColorHunter",
                authorProfileColor = Color.Companion.Magenta,
                hexCodes = listOf("#FF5733", "#33FF57"),
                contentColors = listOf(Color(0xFFFF5733), Color(0xFF33FF57)),
                imageUrl = null,
                commentCount = 5,
                likeCount = 12,
                isLiked = true,
                createdAt = System.currentTimeMillis()
            ),
            FeedItem(
                id = "post_1",
                authorId = userId,
                authorNickname = "ColorHunter",
                authorProfileColor = Color.Companion.Magenta,
                hexCodes = listOf("#FF5733", "#33FF57"),
                contentColors = listOf(Color(0xFFFF5733), Color(0xFF33FF57)),
                imageUrl = null,
                commentCount = 5,
                likeCount = 12,
                isLiked = true,
                createdAt = System.currentTimeMillis()
            ),
            FeedItem(
                id = "post_2",
                authorId = userId,
                authorNickname = "ColorHunter",
                authorProfileColor = Color.Companion.Magenta,
                hexCodes = listOf("#000000", "#FFFFFF"),
                contentColors = listOf(Color.Companion.Black, Color.Companion.White),
                imageUrl = null,
                commentCount = 2,
                likeCount = 30,
                isLiked = false,
                createdAt = System.currentTimeMillis() - 86400000
            )
        )
        emit(fakePosts)
    }
}