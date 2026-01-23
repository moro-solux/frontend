package com.solux.moro.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.solux.moro.data.dto.response.MapPostDetailDto
import com.solux.moro.data.dto.response.MapPostDto
import com.solux.moro.data.service.MapService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val api: MapService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    private var lastCenter: Pair<Double, Double>? = null
    private val mockPosts = listOf(
        MapPostDto(postId = 101L, lat = 37.5466, lng = 126.9647, title = "Mock Cafe"),
        MapPostDto(postId = 102L, lat = 37.5459, lng = 126.9628, title = "Mock Gallery"),
        MapPostDto(postId = 103L, lat = 37.5449, lng = 126.9654, title = "Mock Park"),
    )

    fun onKeywordChange(v: String) {
        _uiState.update { it.copy(keyword = v) }
    }

    fun onLocationPermissionChanged(granted: Boolean) {
        _uiState.update { it.copy(hasFineLocationPermission = granted) }
    }

    fun updateLastKnownLocation(latLng: LatLng?) {
        _uiState.update { it.copy(lastKnownLatLng = latLng) }
    }

    fun loadNearby(lat: Double, lng: Double) = viewModelScope.launch {
        Log.d("MapViewModel", "loadNearby called lat=$lat lng=$lng radius=${_uiState.value.radius}")
        lastCenter = lat to lng

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        runCatching { api.getNearbyPosts(lat, lng, _uiState.value.radius) }
            .onSuccess { response ->
                Log.d("MapViewModel", "loadNearby success=${response.success} count=${response.data.size}")
                val posts = if (response.success && response.data.isNotEmpty()) {
                    response.data
                } else {
                    mockPosts
                }
                _uiState.update { it.copy(posts = posts, isLoading = false) }
            }
            .onFailure { e ->
                Log.e("MapViewModel", "loadNearby failure: ${e.message}", e)
                _uiState.update {
                    it.copy(posts = mockPosts, isLoading = false, errorMessage = e.message)
                }
            }
    }

    fun search() = viewModelScope.launch {
        val keyword = uiState.value.keyword.trim()
        if (keyword.isBlank()) return@launch

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        runCatching { api.searchPosts(keyword = keyword, radius = 5.0) }
            .onSuccess { response ->
                if (response.success && response.data.posts.isNotEmpty()) {
                    val center = LatLng(response.data.centerLat, response.data.centerLng)
                    _uiState.update {
                        it.copy(
                            posts = response.data.posts,
                            searchCenter = center,
                            isLoading = false
                        )
                    }
                } else {
                    val fallbackCenter = lastCenter?.let { LatLng(it.first, it.second) }
                    _uiState.update {
                        it.copy(
                            posts = mockPosts,
                            searchCenter = fallbackCenter,
                            isLoading = false
                        )
                    }
                }
            }
            .onFailure { e ->
                val fallbackCenter = lastCenter?.let { LatLng(it.first, it.second) }
                _uiState.update {
                    it.copy(
                        posts = mockPosts,
                        searchCenter = fallbackCenter,
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
    }

    fun selectPost(postId: Long) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        runCatching { api.getPostDetail(postId) }
            .onSuccess { response ->
                if (response.success) {
                    _uiState.update { it.copy(selectedPost = response.data, isLoading = false) }
                } else {
                    _uiState.update {
                        it.copy(
                            selectedPost = mockDetail(postId),
                            isLoading = false,
                            errorMessage = response.message
                        )
                    }
                }
            }
            .onFailure { e ->
                _uiState.update {
                    it.copy(
                        selectedPost = mockDetail(postId),
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
    }

    fun clearSelection() {
        _uiState.update { it.copy(selectedPost = null) }
    }

    private fun mockDetail(postId: Long) = MapPostDetailDto(
        postId = postId,
        createdAt = "26.01.13.Tue",
        placeName = "Mock Burger",
        addressKo = "219 Hakdong-ro, Gangnam-gu, Seoul",
        addressEn = "219 Hakdong-ro, Gangnam District, Seoul",
        hexCode1 = "905636",
        hexCode2 = "242424",
        hexCode3 = "F7F4F4",
        hexCode4 = "B55923",
        imageUrl = null,
    )
}
