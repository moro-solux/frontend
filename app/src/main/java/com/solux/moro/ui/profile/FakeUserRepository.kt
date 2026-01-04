package com.solux.moro.ui.profile

import com.solux.moro.core.designsystem.theme.MoroPalette
import com.solux.moro.core.designsystem.theme.MoroThemeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeUserRepository : UserRepository {

    private val _user = MutableStateFlow<User?>(
        User(
            id = "1",
            email = "test@test.com",
            nickname = "테스트유저",
            colorPalette = UserColorPalette(
                theme = MoroThemeType.Pastel,
                userColor =MoroPalette.Pastel.Purple400,
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
        TODO("Not yet implemented")
    }
}
