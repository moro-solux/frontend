package com.solux.moro.ui.ProfileColorEdit

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.core.designsystem.theme.colorsOf
import com.solux.moro.ui.profile.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
@HiltViewModel
class ProfileColorEditViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val user = userRepository.user

    val selectedTheme =
        user.map { it?.colorPalette?.theme ?: MoroThemeType.Pastel }
            .stateIn(
                viewModelScope,
                SharingStarted.Companion.WhileSubscribed(5_000),
                MoroThemeType.Pastel
            )

    val colors: StateFlow<List<Color>> =
        selectedTheme
            .map { theme ->
                colorsOf(theme)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun onThemeSelected(theme: MoroThemeType) { //테마 선택
        viewModelScope.launch {
            val current = user.value ?: return@launch
            userRepository.updateUserColorPalette(
                current.colorPalette.copy(theme = theme)
            )
        }
    }

    fun  updateUserColor(userColor: Color) { //사용자 색상 선택
        viewModelScope.launch {
            val current = user.value ?: return@launch
            userRepository.updateUserColorPalette(
                current.colorPalette.copy(userColor = userColor)
            )
        }
    }

}