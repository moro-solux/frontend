package com.solux.moro.data.dto.request


// 비공개/공개 설정 변경용
data class PrivacyRequestDto(
    val isPublic: Boolean
)

// 푸시 알림 설정 변경용
data class NotificationRequestDto(
    val isNotification: Boolean
)