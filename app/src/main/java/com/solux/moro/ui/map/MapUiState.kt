package com.solux.moro.ui.map

import com.solux.moro.data.dto.response.MapPostDetailDto
import com.solux.moro.data.dto.response.MapPostDto

data class MapUiState(
    val keyword: String = "",
    val radius: Double = 1.5,
    val posts: List<MapPostDto> = emptyList(),
    val selectedPost: MapPostDetailDto? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)