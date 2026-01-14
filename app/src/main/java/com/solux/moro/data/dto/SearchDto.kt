package com.solux.moro.data.dto

data class UserSearchResponseDto (
    val content: List<UserSearchDto>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNext: Boolean
)

data class UserSearchDto(
    val userId: Long,
    val userName: String
)