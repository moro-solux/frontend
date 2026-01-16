package com.solux.moro.ui.auth.component

import com.solux.moro.ui.auth.AuthRepository
import com.solux.moro.ui.auth.AuthState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAuthRepository @Inject constructor()  : AuthRepository {

    private val _authState =
        MutableStateFlow<AuthState>(AuthState.UnAuthenticated)
    override val authState: StateFlow<AuthState> =
        _authState.asStateFlow()

    private var currentUserId: Long? = null

    override fun myUserId(): Long = 3

    override suspend fun login(
        email: String,
        password: String
    ) {
        currentUserId = 1
        _authState.value = AuthState.Authenticated
    }

    override suspend fun logout() {
        currentUserId = null
        _authState.value = AuthState.UnAuthenticated
    }
}
