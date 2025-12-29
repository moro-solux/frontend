package com.solux.moro.ui.profile

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.core.designsystem.theme.colorsOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProfileColorViewModel : ViewModel() {

    private val _selectedTheme =
        MutableStateFlow(MoroThemeType.Pastel)
    val selectedTheme = _selectedTheme.asStateFlow()

    val colors: StateFlow<List<Color>> =
        selectedTheme
            .map { theme ->
                colorsOf(theme)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun onThemeSelected(theme: MoroThemeType) {
        _selectedTheme.value = theme
    }
}