package com.solux.moro.ui.profile

import com.solux.moro.core.designsystem.theme.MoroThemeType


data class UserColorPalette(
    val theme: MoroThemeType,
    val userColor: androidx.compose.ui.graphics.Color,
    val paletteColors: List<androidx.compose.ui.graphics.Color>
)