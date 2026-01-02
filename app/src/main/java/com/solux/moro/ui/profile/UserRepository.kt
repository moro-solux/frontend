package com.solux.moro.ui.profile

import com.solux.moro.core.designsystem.theme.MoroThemeType
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val user: StateFlow<User?>

    suspend fun loadUser()
    suspend fun getUserTheme(): MoroThemeType

    suspend fun updateUserColorPalette(
        palette: UserColorPalette
    )

    suspend fun updateNickname(
        nickname: String
    )
}