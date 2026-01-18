package com.solux.moro.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.dto.response.ColorInfoDto
import com.solux.moro.data.repository.ColorMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorMapViewModel @Inject constructor(
    private val repository: ColorMapRepository
) : ViewModel() {

    // 1. 현재 선택된 테마 상태 (기본값: VIVID)
    private val _currentTheme = MutableStateFlow(ColorThemeType.VIVID)
    val currentTheme: StateFlow<ColorThemeType> = _currentTheme.asStateFlow()

    // 2. 현재 테마의 색상 리스트 (서버 데이터)
    private val _colorList = MutableStateFlow<List<ColorInfoDto>>(emptyList())
    val colorList: StateFlow<List<ColorInfoDto>> = _colorList.asStateFlow()

    // 로딩 상태 (필요 시 UI에서 사용)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadThemeData(ColorThemeType.VIVID.name)
    }

    fun updateTheme(theme: ColorThemeType) {
        _currentTheme.value = theme // 1) 현재 테마 상태 업데이트
        loadThemeData(theme.name)   // 2) 해당 테마 데이터 서버 요청
    }

    fun loadThemeData(themeName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getThemeDetails(themeName)
                if (response.isSuccessful) {
                    _colorList.value = response.body()?.data?.colors ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}