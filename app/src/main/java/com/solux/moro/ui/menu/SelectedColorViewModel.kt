package com.solux.moro.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.dto.response.ColorPostDto
import com.solux.moro.data.repository.ColorMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedColorViewModel @Inject constructor(
    private val repository: ColorMapRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<ColorPostDto>>(emptyList())
    val posts: StateFlow<List<ColorPostDto>> = _posts.asStateFlow()

    fun loadColorPosts(colorId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getColorPosts(colorId, page = 0, size = 10)

                if (response.isSuccessful) {
                    _posts.value = response.body()?.data?.content ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}