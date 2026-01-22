package com.solux.moro.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.solux.moro.data.dto.BaseResponse
import com.solux.moro.data.dto.NicknameCheckData
import com.solux.moro.data.network.NetworkModule
import com.solux.moro.data.repository.menurepo.SettingPreferenceManager
import com.solux.moro.data.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import javax.inject.Inject

sealed interface NicknameCheckState {
    data object Idle : NicknameCheckState
    data object Checking : NicknameCheckState
    data object Available : NicknameCheckState
    data object Unavailable : NicknameCheckState
    data class Error(val message: String) : NicknameCheckState
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService,
    private val settingPreferenceManager: SettingPreferenceManager
) : ViewModel() {

    private val gson = Gson()

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var nicknameCheckState by mutableStateOf<NicknameCheckState>(NicknameCheckState.Idle)
        private set

    var isRegistering by mutableStateOf(false)
        private set

    fun resetNicknameCheck() {
        nicknameCheckState = NicknameCheckState.Idle
    }

    suspend fun checkNickname(userName: String) {
        val trimmed = userName.trim()
        Log.d("AUTH_LOG", "checkNickname called with userName=$trimmed")
        if (trimmed.isBlank()) {
            nicknameCheckState = NicknameCheckState.Error("닉네임을 입력해주세요")
            return
        }
        nicknameCheckState = NicknameCheckState.Checking
        nicknameCheckState = withContext(Dispatchers.IO) {
            runCatching {
                Log.d("AUTH_LOG", "checkNickname request start")
                authService.checkNickname(trimmed)
            }.fold(
                onSuccess = { response ->
                    val rawBody = response.body()?.string()
                    val rawError = response.errorBody()?.string()
                    val payload = rawBody ?: rawError
                    Log.d(
                        "AUTH_LOG",
                        "checkNickname response code=${response.code()} body=${payload ?: "null"}"
                    )
                    if (payload.isNullOrBlank()) {
                        return@fold NicknameCheckState.Error("닉네임 확인에 실패했습니다")
                    }
                    runCatching {
                        val type = object : TypeToken<BaseResponse<NicknameCheckData>>() {}.type
                        gson.fromJson<BaseResponse<NicknameCheckData>>(payload, type)
                    }.fold(
                        onSuccess = { parsed ->
                            Log.d(
                                "AUTH_LOG",
                                "checkNickname parsed success=${parsed.success} status=${parsed.status}"
                            )
                            if (parsed.success) {
                                if (parsed.data.available && !parsed.data.exists) {
                                    NicknameCheckState.Available
                                } else {
                                    NicknameCheckState.Unavailable
                                }
                            } else {
                                NicknameCheckState.Error(
                                    parsed.message.ifBlank { "닉네임 확인에 실패했습니다" }
                                )
                            }
                        },
                        onFailure = { e ->
                            Log.e("AUTH_LOG", "checkNickname parse failed", e)
                            NicknameCheckState.Error("닉네임 확인에 실패했습니다")
                        }
                    )
                },
                onFailure = { e ->
                    Log.e("AUTH_LOG", "checkNickname request failed", e)
                    NicknameCheckState.Error(e.message ?: "닉네임 확인에 실패했습니다")
                }
            )
        }
    }

    private fun normalizeToken(token: String): String {
        return token.removePrefix("Bearer ").trim()
    }

    fun saveToken(token: String) {
        val normalized = normalizeToken(token)
        if (normalized.isBlank()) return
        NetworkModule.token = normalized
        settingPreferenceManager.setAccessToken(normalized)
    }

    suspend fun completeRegistration(
        email: String,
        nickname: String,
        sensitivity: Int
    ): Boolean {
        if (isRegistering) return false
        isRegistering = true
        return try {
            val response = authService.completeRegistration(
                email = email,
                nickname = nickname,
                sensitivity = sensitivity
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
        } finally {
            isRegistering = false
        }
    }
}
