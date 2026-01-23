package com.solux.moro.data.repository

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.dto.ColorThemeDto
import com.solux.moro.data.dto.MainColorEditRequest
import com.solux.moro.data.dto.UserProfileEditRequest
import com.solux.moro.data.mapper.ColorMapper
import com.solux.moro.data.mapper.toDomain
import com.solux.moro.data.mapper.toStatsDomain
import com.solux.moro.data.model.ProfileFeedItem
import com.solux.moro.data.model.SearchResultPage
import com.solux.moro.data.model.User
import com.solux.moro.data.model.UserColorPalette
import com.solux.moro.data.model.UserStats
import com.solux.moro.data.service.UserService
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
): UserRepository{
    private val _user = MutableStateFlow<User>(User(
        id = -1L,
        nickname = "",
        userColorHex = "",
        colorPalette = UserColorPalette(userColor=null,paletteColors = emptyList<Color>()),
        visible = false
    ))
    override val user: StateFlow<User> = _user.asStateFlow()
    private val _currentUserId = MutableStateFlow(-1L)
    override val currentUserId: StateFlow<Long> = _currentUserId.asStateFlow()

    private val _userStats = MutableStateFlow<UserStats?>(null)
    override val userStats: StateFlow<UserStats?> = _userStats.asStateFlow()

    override suspend fun loadUser(userId: Long) {
        try {
            Log.d("loadUserTest", "loadUser ID: $userId")
            val response =
                if(userId==-1L) {
                    userService.getMyProfile()
                }
                else  userService.getUserProfile(userId = userId)

            if (response.success) {
                if (userId == -1L) {
                    _currentUserId.value = response.data.userId
                    Log.d("loadUserTest", "내 ID 저장 완료: $currentUserId")
                }
                Log.d("loadUserTest", "서버가 보내준 실제 이름: ${response.data.userName}")
                _user.value = response.data.toDomain()
                _userStats.value= response.data.toStatsDomain()
                Log.d("loadUserTest!!!!", "loadUser ID: ${_user.value.id}")
            }
            else {
                Log.e("loadUserTest", "success== false")
            }
        } catch (e: Exception) {
            Log.e("loadUserTest", "에러 타입: ${e.javaClass.simpleName}")
            Log.e("loadUserTest", "에러 메시지: ${e.message}")
        }
    }

    override fun getUserPosts(userId: Long, viewType: String, colorId: Int?): Flow<List<ProfileFeedItem>> = flow {
        val response = userService.getUserProfileFeed(userId = userId)

        if (response.success && response.data != null) {
            val postList = response.data.page.content
            Log.d("FeedRepository", "유저 게시글 로딩 성공: $postList")
            if(colorId!=null)
                Log.d("FeedRepository", "colorId별 유저 게시글 로딩 성공: $colorId")
            val userItems = postList.map { it.toDomain() }
            emit(userItems)
        } else {
            Log.e("FeedRepository", "응답 실패: ${response.message}")
            emit(emptyList())
        }
    }.catch { e ->
        Log.e("FeedRepository", "유저 게시글 로딩 에러: ${e.message}")
        emit(emptyList())
    }
    //유저 컬러 팔레트 수정
    override suspend fun updateUserColorPalette(palette: UserColorPalette): Result<Unit> {
        return try {
            val request = MainColorEditRequest(
                colorIds = palette.paletteColors?.mapNotNull {
                    ColorMapper.toIdFromComposeColor(it)
                } ?: emptyList()
            )
            Log.d("request","request $request")
            val response = userService.mainColorEdit(request)

            if (response.success) {
                loadUser(_user.value.id)
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //프로필 수정
    override suspend fun updateProfile(nickname: String?, userColorId: Int?,userColorHex: String?)
            : Result<Unit>{
        val colorHexToSend = userColorHex?.removePrefix("#")
        val request = UserProfileEditRequest(
            userName = nickname,
            userColorId = userColorId,
            userColorHex = colorHexToSend
        )

        return try {
            val response = userService.profileEdit(request)
            if (response.success) {
                loadUser(currentUserId.value)
                Result.success(Unit)
            }
            else {
                Result.failure(Exception("응답 에러: ${response.message}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getColorUnlockInfo(): Result<List<ColorThemeDto>> {
        return try {
            val response = userService.getColorUnlockInfo()
            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    //유저 검색
    override suspend fun searchUsers(
        query: String,
        page: Int
    ): Result<SearchResultPage> {
        return try {
            val response = userService.searchUser(query)
            val searchResponseDto = response.data
            val searchResultPage = searchResponseDto.toDomain()
            Result.success(searchResultPage)

        } catch (e: Exception) {
            Log.e("SearchTest", "에러 타입: ${e.javaClass.simpleName}")
            Log.e("SearchTest", "에러 메시지: ${e.message}")
            Result.failure(e)
        }
    }

}