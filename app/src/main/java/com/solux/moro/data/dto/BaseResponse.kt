package com.solux.moro.data.dto

data class BaseResponse<T>(
    val success: Boolean,
    val status: Int,
    val message: String,
    val data: T? = null
)