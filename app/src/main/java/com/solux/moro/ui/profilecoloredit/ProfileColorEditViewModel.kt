package com.solux.moro.ui.profilecoloredit

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.core.designsystem.theme.colorsOf
import com.solux.moro.core.domain.UserRepository
import com.solux.moro.data.dto.ColorThemeDto
import com.solux.moro.data.mapper.ColorMapper
import com.solux.moro.ui.profile.component.ColorCellData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileColorEditViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val user = userRepository.user

    private val _selectedTheme = MutableStateFlow(MoroThemeType.Pastel)
    val selectedTheme: StateFlow<MoroThemeType> = _selectedTheme.asStateFlow()

    private val _selectedColor = MutableStateFlow(Color.White)
    val selectedColor: StateFlow<Color> = _selectedColor.asStateFlow()

    private val _themeDataFromServer = MutableStateFlow<List<ColorThemeDto>>(emptyList())

    private val _isSaveEnabled = MutableStateFlow(false)
    val isSaveEnabled: StateFlow<Boolean> = _isSaveEnabled.asStateFlow()

    init {
        fetchColorUnlockInfo()
    }
    fun onThemeSelected(theme: MoroThemeType) {
        _selectedTheme.value = theme
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

    fun  updateUserColor(userColor: Color) { //사용자 색상 선택
        _selectedColor.value = userColor
        _isSaveEnabled.value = true
        viewModelScope.launch {
            val current = user.value ?: return@launch
            userRepository.updateUserColorPalette(
                current.colorPalette.copy(userColor = userColor)
            )
        }
    }

    fun onSaveUserColor() {//사용자 색상 저장
        viewModelScope.launch {
            val currentColor = _selectedColor.value
            val userColorId=ColorMapper.toIdFromComposeColor(currentColor)
            val userColorHex = userColorId.let { ColorMapper.toHexFromId(it) }
            userRepository.updateProfile(null,
                userColorId=userColorId,
                userColorHex=userColorHex)
                .onSuccess {
                    _isSaveEnabled.value = false
                    Log.d("ProfileColorEditViewModel", "사용자 색상 수정 성공")
                }
                .onFailure {
                    Log.d("ProfileColorEditViewModel", "프로필 수정 실페")
                }
        }
    }

}