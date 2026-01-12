package com.solux.moro.ui.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authState: StateFlow<AuthState>

    fun myUserId(): Long

    suspend fun login(
        email: String,
        password: String
    )
    suspend fun logout()
}

sealed interface AuthState {
    object Authenticated : AuthState
    object UnAuthenticated : AuthState
}

