package com.solux.moro.data.model

data class SearchUser(
    val id: Long,
    val nickname: String,
)

data class SearchResultPage(
    val users: List<SearchUser>,
    val isLastPage: Boolean,
    val pageNumber: Int
)