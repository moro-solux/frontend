package com.solux.moro.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.repository.menurepo.SettingPreferenceManager
import com.solux.moro.data.repository.menurepo.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    private val preferenceManager: SettingPreferenceManager
) : ViewModel() {

    private val _isPublic = MutableStateFlow(preferenceManager.getVisibility())
    val isPublic = _isPublic.asStateFlow()

    private val _isNotificationEnabled = MutableStateFlow(preferenceManager.getNotification())
    val isNotificationEnabled = _isNotificationEnabled.asStateFlow()

    // 1. 공개/비공개 설정 변경
    fun onVisibilityChanged(isVisible: Boolean) {
        viewModelScope.launch {
            val result = settingRepository.updateVisibility(isVisible)
            if (result.isSuccess) {
                preferenceManager.setVisibility(isVisible)
                _isPublic.value = isVisible
                println("로그: 서버/로컬 공개 설정 저장 성공 -> $isVisible")
            }
        }
    }

    // 2. 알림 설정 변경
    fun onPushSettingsChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            val result = settingRepository.updateNotification(isEnabled)
            if (result.isSuccess) {
                preferenceManager.setNotification(isEnabled)
                _isNotificationEnabled.value = isEnabled
                println("로그: 서버/로컬 알림 설정 저장 성공 -> $isEnabled")
            }
        }
    }

    // 3. 로그아웃 실행
    fun performLogout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = settingRepository.logout()
            if (result.isSuccess) {
                //서버 로그아웃 성공 시 저장된 토큰과 설정 데이터 삭제
                preferenceManager.clearAuthData()

                println("로그: 서버 로그아웃 완료 및 로컬 토큰 삭제 성공")
                onLogoutSuccess()
            } else {
                println("로그: 로그아웃 실패 -> ${result.exceptionOrNull()}")
            }
        }
    }
}