package com.solux.moro.ui.paletteedit

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.core.designsystem.theme.colorsOf
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.dto.ColorThemeDto
import com.solux.moro.ui.profile.component.ColorCellData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class PaletteEditViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val user = userRepository.user
    private val _selectedTheme = MutableStateFlow(MoroThemeType.Pastel)
    val selectedTheme: StateFlow<MoroThemeType> = _selectedTheme.asStateFlow()
    private val _selectedColor = MutableStateFlow(Color.White)
    val selectedColor: StateFlow<Color> = _selectedColor.asStateFlow()
    private val _themeDataFromServer = MutableStateFlow<List<ColorThemeDto>>(emptyList())
    private val _editingColorIndex = MutableStateFlow<Int?>(0)
    val editingColorIndex = _editingColorIndex.asStateFlow()
    private val _tempPaletteColors = MutableStateFlow<List<Color>>(emptyList())
    val tempPaletteColors = _tempPaletteColors.asStateFlow()

    private val _isSaveEnabled = MutableStateFlow(false)
    val isSaveEnabled: StateFlow<Boolean> = _isSaveEnabled.asStateFlow()


    init {
        fetchColorUnlockInfo()
        viewModelScope.launch {
            user.collect { u ->
                val serverColors = u?.colorPalette?.paletteColors ?: emptyList()
                //Log.d("PaletteData", "서버 Color 객체: $serverColors")

                val initializedList = if (serverColors.isEmpty()) {
                    List(6) { Color.Transparent }
                }else {
                    serverColors.take(6) + List(maxOf(0, 6 - serverColors.size)) { Color.Transparent }
                }

                if (_tempPaletteColors.value.isEmpty()) {
                    _tempPaletteColors.value = initializedList
                    Log.d("PaletteData", "초기화된 리스트 개수: ${_tempPaletteColors.value.size}")
                }
            }
        }
    }

    fun setEditingIndex(index: Int) {
        _editingColorIndex.value = index
    }

    //저장 누르기 전 색상 선택
    fun onColorSelected(selectedColor: Color) {
        val index = _editingColorIndex.value ?: return
        Log.d("PaletteData", "선택된 인덱스: $index, 선택된 색상: $selectedColor")


        _selectedColor.value = selectedColor
        _isSaveEnabled.value = true

        val currentTemp = _tempPaletteColors.value.toMutableList()
        while (currentTemp.size <= index || currentTemp.size < 6) {
            currentTemp.add(Color.Transparent)
        }

        currentTemp[index] = selectedColor

        if (index in currentTemp.indices) {
            currentTemp[index] = selectedColor
            _tempPaletteColors.value = currentTemp
            //Log.d("PaletteData", "현재 임시 팔레트 리스트 상태: ${_tempPaletteColors.value}")
        }
    }


    val colors: StateFlow<List<ColorCellData>> = combine(
        selectedTheme,
        _selectedColor,
        _themeDataFromServer
    ) { theme, selectedColor, serverData ->
        val currentThemeData = serverData.find {
            it.themeName.equals(theme.name, ignoreCase = true)
        }

        currentThemeData?.colors?.map { dto ->
            val composeColor = Color(android.graphics.Color.parseColor("#${dto.hexCode}"))
            ColorCellData(
                color = composeColor,
                isSelected = composeColor == selectedColor,
                isLocked = !dto.unlocked
            )
        } ?: colorsOf(theme).map {
            ColorCellData(
                color = it,
                isSelected = it == selectedColor,
                isLocked = false
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )


    val paletteColors: StateFlow<List<Color>> =
        user
            .map { it?.colorPalette?.paletteColors ?: emptyList() }
            .stateIn(
                viewModelScope,
                SharingStarted.Companion.WhileSubscribed(5_000),
                emptyList()
            )



    enum class ArrowWay {
        LEFT,
        RIGHT
    }
    fun onThemeSelected( arrowWay: ArrowWay) {
        val themes = MoroThemeType.entries.toTypedArray()
        val currentIndex = themes.indexOf(_selectedTheme.value)

        val nextIndex = when (arrowWay) {
            ArrowWay.RIGHT ->
                (currentIndex + 1) % themes.size
            ArrowWay.LEFT ->
                (currentIndex - 1 + themes.size) % themes.size
        }

        _selectedTheme.value = themes[nextIndex]
    }

    private fun fetchColorUnlockInfo() {
        viewModelScope.launch {
            userRepository.getColorUnlockInfo() //리스트 반환
                .onSuccess { themeList ->
                    _themeDataFromServer.value = themeList
                }
                .onFailure { error ->
                    Log.e("ProfileColorEditViewModel", "잠금 정보 로드 실패: ${error.message}")
                }
        }
    }

    fun savePalette(){ // 팔레트 업데이트
        viewModelScope.launch {
            val current = user.value ?: return@launch

            val filteredColors = _tempPaletteColors.value.filter { color ->
                color != Color.Transparent
            }
            Log.d("PaletteSave", "원본 개수: ${_tempPaletteColors.value.size}, 필터링 후 개수: ${filteredColors.size}")


            userRepository.updateUserColorPalette(
                current.colorPalette.copy(
                    paletteColors = filteredColors),
                ).onSuccess {
                userRepository.loadUser()
                _editingColorIndex.value = 0
                _isSaveEnabled.value = false
                Log.d("PaletteEdit","색상 편집 완료 $filteredColors")
            }.onFailure {error ->
                Log.e("PaletteEdit", "저장 실패: ${error.message}")
            }
        }
    }

}