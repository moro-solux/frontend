package com.solux.moro.ui.profile

import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.ui.home.FeedItem
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