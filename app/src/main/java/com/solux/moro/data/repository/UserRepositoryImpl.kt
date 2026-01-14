package com.solux.moro.data.repository

import android.util.Log
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.dto.MainColorEditRequest
import com.solux.moro.data.dto.UserProfileEditRequest
import com.solux.moro.data.mapper.ColorMapper
import com.solux.moro.data.mapper.toDomain
import com.solux.moro.data.model.FeedItem
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
import kotlinx.coroutines.flow.flowOf

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
): UserRepository{
    private val _user = MutableStateFlow<User>(User(
        id = -1,
        nickname = "",
        userColorHex = "#FFFFFF",
        colorPalette = UserColorPalette(userColor=null,paletteColors = emptyList()),
        visible = false
    ))
    override val user: StateFlow<User> = _user.asStateFlow()

    private val _userStats = MutableStateFlow<UserStats?>(null)
    override val userStats: StateFlow<UserStats?> = _userStats.asStateFlow()

    override suspend fun loadUser(userId: Long) {
        try {
            Log.d("loadUserTest", "loadUser ID: $userId")
            val response = userService.getUserProfile(userId = userId)
            Log.d("loadUserTest", "서버 응답 성공 여부: ${response.success}")
            if (response.success) {
                _user.value = response.data.toDomain()
                Log.d("loadUserTest", "데이터 매핑 완료: ${_user.value.nickname}")
            }
            else {
                Log.e("loadUserTest", "success== false")
            }
        } catch (e: Exception) {
            Log.e("loadUserTest", "에러 타입: ${e.javaClass.simpleName}")
            Log.e("loadUserTest", "에러 메시지: ${e.message}")
            if (e is retrofit2.HttpException) {
                val errorStatus = e.code() // HTTP 상태 코드 (예: 401)
                val errorBody = e.response()?.errorBody()?.string() // 이게 서버가 보낸 HTML/텍스트입니다.

                Log.e("loadUserTest", "HTTP Status: $errorStatus")
                Log.e("loadUserTest", "Server Response Body: $errorBody")
            }
            // 2. 파싱 에러(JSON이 아닐 때)인 경우
            else if (e is com.google.gson.JsonSyntaxException || e is java.io.IOException) {
                Log.e("loadUserTest", "Parsing Error or Network Issue: ${e.message}")
            }
            else {
                Log.e("loadUserTest", "Unknown Error: ${e.message}")
            }
        }
    }

    override fun getUserPosts(userId: Long): Flow<List<FeedItem>> {
        return flowOf(emptyList())
        //TODO("Not yet implemented")
    }

    //유저 컬러 팔레트 수정
    override suspend fun updateUserColorPalette(palette: UserColorPalette) {
        val request = MainColorEditRequest(
            colorIds = palette.paletteColors?.mapNotNull {
                ColorMapper.toIdFromComposeColor(it)
            } ?: emptyList()
        )
        val response = userService.mainColorEdit(request)
        if (response.success) {
            loadUser()
        }
    }

    //프로필 수정
    override suspend fun updateProfile(nickname: String?, userColorId: Int?,userColorHex: String?) {
        val currentData = _user.value
        val finalUserName = nickname
            ?: currentData?.nickname
            ?: ""

        val finalColorId = userColorId
            ?: ColorMapper.toIdFromComposeColor(currentData?.colorPalette?.userColor)
            ?: 0

        val finalColorHex = userColorHex
            ?: ColorMapper.toHexFromId(finalColorId)
            ?: ""

        val request = UserProfileEditRequest(
            userName = finalUserName,
            userColorId = finalColorId,
            userColorHex = finalColorHex
        )

        try {
            val response = userService.profileEdit(request)
            if (response.success) {
                loadUser()
            }
        } catch (e: Exception) {
            // 에러
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
            Result.failure(e)
        }
    }

}