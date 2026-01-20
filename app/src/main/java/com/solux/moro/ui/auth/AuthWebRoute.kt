package com.solux.moro.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AuthWebRoute() {
    val authUrl = remember { "https://moro-be.store/auth/login/google" }

    AuthWebScreen(
        modifier = Modifier.fillMaxSize(),
        url = authUrl
    )
}
