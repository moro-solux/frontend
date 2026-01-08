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

    private var currentUserId: String? = null

    override fun myUserId(): String = "test-user-id"

    override suspend fun login(
        email: String,
        password: String
    ) {
        currentUserId = "fake_user_id_123"
        _authState.value = AuthState.Authenticated
    }

    override suspend fun logout() {
        currentUserId = null
        _authState.value = AuthState.UnAuthenticated
    }
}
