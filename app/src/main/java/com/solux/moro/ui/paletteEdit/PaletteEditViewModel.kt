package com.solux.moro.ui.paletteEdit

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.core.designsystem.theme.colorsOf
import com.solux.moro.ui.profile.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
@HiltViewModel
class PaletteEditViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val user = userRepository.user
    val selectedTheme =
        user.map { it?.colorPalette?.theme ?: MoroThemeType.Pastel }
            .stateIn(
                viewModelScope,
                SharingStarted.Companion.WhileSubscribed(5_000),
                MoroThemeType.Pastel
            )

    private val _editingColorIndex =
        MutableStateFlow<Int>(0)

    val colors: StateFlow<List<Color>> =
        selectedTheme
            .map { theme -> colorsOf(theme) }
            .stateIn(
                viewModelScope,
                SharingStarted.Companion.WhileSubscribed(5_000),
                emptyList()
            )

    val paletteColors: StateFlow<List<Color>> =
        user
            .map { it?.colorPalette?.paletteColors ?: emptyList() }
            .stateIn(
                viewModelScope,
                SharingStarted.Companion.WhileSubscribed(5_000),
                emptyList()
            )

    val editingColorIndex=_editingColorIndex.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.loadUser()
        }
    }


    fun updatePaletteColor(index: Int, color: Color) { // 팔레트 업데이트
        viewModelScope.launch {
            val current = user.value ?: return@launch
            val newColors = current.colorPalette.paletteColors.toMutableList()
            newColors[index] = color

            userRepository.updateUserColorPalette(
                current.colorPalette.copy(
                    paletteColors = newColors
                )
            )
        }
    }

}