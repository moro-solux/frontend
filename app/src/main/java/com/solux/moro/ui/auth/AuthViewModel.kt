package com.solux.moro.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.solux.moro.data.dto.CompleteRegistrationRequest
import com.solux.moro.data.network.NetworkModule
import com.solux.moro.data.repository.menurepo.SettingPreferenceManager
import com.solux.moro.data.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService,
    private val settingPreferenceManager: SettingPreferenceManager
) : ViewModel() {

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun saveToken(token: String) {
        NetworkModule.token = token
        settingPreferenceManager.setAccessToken(token)
    }

    suspend fun completeRegistration(
        email: String,
        nickname: String,
        sensitivity: Int
    ): Boolean {
        return try {
            val response = authService.completeRegistration(
                CompleteRegistrationRequest(
                    email = email,
                    nickname = nickname,
                    sensitivity = sensitivity
                )
            )
            val body = response.body()
            val headerToken = response.headers()["Authorization"]
                ?.removePrefix("Bearer ")
                ?.trim()
                ?.ifBlank { null }
            val token = body?.data?.ifBlank { null } ?: headerToken
            if (response.isSuccessful && !token.isNullOrBlank() && body?.success == true) {
                saveToken(token)
                errorMessage = null
                true
            } else {
                errorMessage = body?.message?.ifBlank {
                    "회원가입에 실패했습니다"
                }
                false
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "회원가입에 실패했습니다"
            false
        }
    }
}
