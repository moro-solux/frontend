package com.solux.moro.data.mapper

import com.solux.moro.data.dto.UserSearchDto
import com.solux.moro.data.dto.UserSearchResponseDto
import com.solux.moro.data.model.SearchResultPage
import com.solux.moro.data.model.SearchUser

fun UserSearchDto.toDomain() = SearchUser(
    id = userId,
    nickname = userName
)

fun UserSearchResponseDto.toDomain() = SearchResultPage(
    users = content.map { it.toDomain() },
    isLastPage = !hasNext,
    pageNumber = currentPage
)