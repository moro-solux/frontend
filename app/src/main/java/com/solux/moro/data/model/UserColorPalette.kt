package com.solux.moro.data.model

import androidx.compose.ui.graphics.Color
import com.solux.moro.core.designsystem.theme.MoroThemeType

data class UserColorPalette(
    val theme: MoroThemeType,
    val userColor: Color,
    val paletteColors: List<Color>
)