package com.solux.moro.core.domain

import com.solux.moro.data.dto.ColorThemeDto
import com.solux.moro.data.model.ProfileFeedItem
import com.solux.moro.data.model.SearchResultPage
import com.solux.moro.data.model.User
import com.solux.moro.data.model.UserColorPalette
import com.solux.moro.data.model.UserStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val user: StateFlow<User>
    val userStats: StateFlow<UserStats?>
    val currentUserId:StateFlow<Long>

    suspend fun loadUser(userId:Long=5)

    fun getUserPosts(userId: Long): Flow<List<ProfileFeedItem>>

    suspend fun getColorUnlockInfo():Result<List<ColorThemeDto>>

    suspend fun updateUserColorPalette(
        palette: UserColorPalette
    ): Result<Unit>

    suspend fun updateProfile(
        nickname: String?,
        userColorId: Int?,
        userColorHex: String?
    ): Result<Unit>

    suspend fun searchUsers(
        query: String,
        page: Int = 0
    ): Result<SearchResultPage>
}