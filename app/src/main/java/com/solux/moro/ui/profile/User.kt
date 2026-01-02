package com.solux.moro.ui.profile

data class User(
    val id: String,
    val email: String,
    val nickname: String,
    val colorPalette: UserColorPalette
)
