package com.solux.moro.data.dto

data class CompleteRegistrationRequest(
    val email: String,
    val nickname: String,
    val sensitivity: Int
)

data class AuthResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: String? = null
)

data class NicknameCheckRequest(
    val userName: String
)

data class NicknameCheckData(
    val available: Boolean,
    val exists: Boolean
)
