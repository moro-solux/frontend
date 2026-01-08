package com.solux.moro.data.repository

import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.data.model.FeedItem
import com.solux.moro.data.model.UserColorPalette
import com.solux.moro.data.model.User
import com.solux.moro.data.model.UserStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val user: StateFlow<User?>
    val userStats: StateFlow<UserStats?>


    suspend fun loadUser()
    suspend fun getUserTheme(): MoroThemeType
    fun getUserPosts(userId: String): Flow<List<FeedItem>>

    suspend fun updateUserColorPalette(
        palette: UserColorPalette
    )

    suspend fun updateNickname(
        nickname: String
    )
}