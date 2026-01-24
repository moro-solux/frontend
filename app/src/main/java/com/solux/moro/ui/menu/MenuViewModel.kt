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

    // 1. 상태 변수 (초기값은 로컬 저장소에서 가져와서 UI가 바로 뜨도록 함)
    private val _isPublic = MutableStateFlow(preferenceManager.getVisibility())
    val isPublic = _isPublic.asStateFlow()

    private val _isNotificationEnabled = MutableStateFlow(preferenceManager.getNotification())
    val isNotificationEnabled = _isNotificationEnabled.asStateFlow()

    // ★ 2. 초기화 블록: 앱이 켜질 때 서버에서 '진짜 최신 상태'를 가져옴
    init {
        fetchServerSettings()
    }

    // ★ 3. 서버 상태 동기화 함수
    private fun fetchServerSettings() {
        viewModelScope.launch {
            // (1) 공개/비공개 상태 조회
            settingRepository.getPrivacyStatus()
                .onSuccess { serverValue ->
                    // 서버 값이 로컬과 다르면 갱신
                    if (_isPublic.value != serverValue) {
                        _isPublic.value = serverValue
                        preferenceManager.setVisibility(serverValue)
                        println("로그: 서버 공개 상태 동기화 완료 -> $serverValue")
                    }
                }
                .onFailure {
                    println("로그: 공개 상태 조회 실패 (기존 로컬 값 유지)")
                }

            // (2) 알림 상태 조회
            settingRepository.getNotificationStatus()
                .onSuccess { serverValue ->
                    if (_isNotificationEnabled.value != serverValue) {
                        _isNotificationEnabled.value = serverValue
                        preferenceManager.setNotification(serverValue)
                        println("로그: 서버 알림 상태 동기화 완료 -> $serverValue")
                    }
                }
                .onFailure {
                    println("로그: 알림 상태 조회 실패 (기존 로컬 값 유지)")
                }
        }
    }

    // 4. 사용자가 공개/비공개 스위치를 눌렀을 때
    fun onVisibilityChanged(isVisible: Boolean) {
        viewModelScope.launch {
            // 일단 UI를 먼저 바꿔서 반응성 높이기 (Optimistic Update)
            _isPublic.value = isVisible

            val result = settingRepository.updateVisibility(isVisible)
            if (result.isSuccess) {
                preferenceManager.setVisibility(isVisible) // 성공하면 로컬 저장
                println("로그: 서버 공개 설정 변경 성공 -> $isVisible")
            } else {
                // 실패하면 다시 원래대로 되돌리기 (Rollback)
                _isPublic.value = !isVisible
                println("로그: 공개 설정 변경 실패")
            }
        }
    }

    // 5. 사용자가 알림 스위치를 눌렀을 때
    fun onPushSettingsChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            _isNotificationEnabled.value = isEnabled

            val result = settingRepository.updateNotification(isEnabled)
            if (result.isSuccess) {
                preferenceManager.setNotification(isEnabled)
                println("로그: 서버 알림 설정 변경 성공 -> $isEnabled")
            } else {
                _isNotificationEnabled.value = !isEnabled
                println("로그: 알림 설정 변경 실패")
            }
        }
    }

    // 6. 로그아웃
    fun performLogout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = settingRepository.logout()

            // 성공 여부와 관계없이 로컬 데이터 삭제하고 나감 (사용자 경험 우선)
            preferenceManager.clearAuthData()
            onLogoutSuccess()

            if (result.isSuccess) {
                println("로그: 서버 로그아웃 정상 처리됨")
            } else {
                println("로그: 서버 로그아웃 요청 실패 (하지만 로컬에선 로그아웃 처리함)")
            }
        }
    }
}