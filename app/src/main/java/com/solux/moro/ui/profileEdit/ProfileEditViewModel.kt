package com.solux.moro.ui.profileEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.ui.profile.UserRepository
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    val user = userRepository.user

    var nicknameInput by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            userRepository.loadUser()
        }
    }

    fun onNicknameChange(text: String) {
        nicknameInput = text
    }

    fun onSaveNickname() {
        viewModelScope.launch {
            if (nicknameInput.isBlank()) return@launch

            userRepository.updateNickname(nicknameInput)
        }
    }
}