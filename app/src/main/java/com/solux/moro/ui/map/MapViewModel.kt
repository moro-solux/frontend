package com.solux.moro.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
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
        lastCenter = lat to lng

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        runCatching { api.getNearbyPosts(lat, lng, _uiState.value.radius) }
            .onSuccess { list -> _uiState.update { it.copy(posts = list, isLoading = false) } }
            .onFailure { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
    }

    fun search() = viewModelScope.launch {
        val keyword = uiState.value.keyword.trim()
        if (keyword.isBlank()) return@launch

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        runCatching { api.searchPosts(keyword = keyword, radius = 5.0) }
            .onSuccess { list -> _uiState.update { it.copy(posts = list, isLoading = false) } }
            .onFailure { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
    }

    fun selectPost(postId: Long) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        runCatching { api.getPostDetail(postId) }
            .onSuccess { detail -> _uiState.update { it.copy(selectedPost = detail, isLoading = false) } }
            .onFailure { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
    }

    fun clearSelection() {
        _uiState.update { it.copy(selectedPost = null) }
    }
}
