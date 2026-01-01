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

class PaletteColorViewModel : ViewModel() {

    private val _selectedTheme =
        MutableStateFlow(MoroThemeType.Pastel)
    private val _selectedColors =
        MutableStateFlow<List<Color>>(
            listOf(
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent,
                Color.Transparent)
        )
    private val _editingColorIndex =
            MutableStateFlow<Int>(0)

    val selectedTheme = _selectedTheme.asStateFlow()
    val selectedColors=_selectedColors.asStateFlow()
    val editingColorIndex=_editingColorIndex.asStateFlow()


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