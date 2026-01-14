package com.solux.moro.ui.profilecoloredit

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.core.designsystem.theme.colorsOf
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.mapper.ColorMapper
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
class ProfileColorEditViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val user = userRepository.user

    private val _selectedTheme = MutableStateFlow(MoroThemeType.Pastel)
    val selectedTheme: StateFlow<MoroThemeType> = _selectedTheme.asStateFlow()

    fun onThemeSelected(theme: MoroThemeType) {
        _selectedTheme.value = theme
    }

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


    fun  updateUserColor(userColor: Color) { //사용자 색상 선택
        viewModelScope.launch {
            val current = user.value ?: return@launch
            userRepository.updateUserColorPalette(
                current.colorPalette.copy(userColor = userColor)
            )
        }
    }

    fun onSaveUserColor(userColor: Color) {
        viewModelScope.launch {
            val userColorId=ColorMapper.toIdFromComposeColor(userColor)
            userRepository.updateProfile(null,
                userColorId=userColorId
                ,ColorMapper.toHexFromId(userColorId!!))
        }
    }

}