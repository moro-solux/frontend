package com.solux.moro.ui.profileedit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.dto.UserProfileEditRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    val user = userRepository.user
    var nicknameInput by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            userRepository.loadUser(user.value.id)
        }
    }

    fun onNicknameChange(text: String) {
        nicknameInput = text
    }

    fun onSaveNickname() {
        viewModelScope.launch {
            Log.d("ProfileEditScreen", "저장 시도하는 닉네임: $nicknameInput")
            val request = UserProfileEditRequest(userName = nicknameInput)
            val result = userRepository.updateProfile(nicknameInput,null,null)
            if (result.isSuccess) {
                Log.d("ProfileEditScreen", "저장 성공: $nicknameInput")
            }
            else {
                val error = result.exceptionOrNull()
                Log.e("ProfileEditScreen", "저장 실패 원인: ${error?.message}")
            }
        }
    }
}