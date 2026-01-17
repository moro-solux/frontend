package com.solux.moro.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.moro.data.dto.request.ColorItemDto // DTO import 필요
import com.solux.moro.data.repository.UploadRepository
import com.solux.moro.data.repository.location.LocationRepository
import com.solux.moro.ui.camera.PlaceData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import android.location.Geocoder
import com.solux.moro.BuildConfig
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest


// 상태 관리를 위한 데이터 클래스
data class UploadState(
    val step: Int = 0,
    val capturedUri: Uri? = null,
    val draftId: Long? = null,
    val nearbyPlaces: List<PlaceData> = emptyList(),
    val detectedLocation: String = "위치 분석 중...",

    // 사진 찍을 때 잡은 좌표 저장
    val currentLat: Double = 37.55,
    val currentLng: Double = 126.97,

    // HexCode, "42%"
    val analyzedColors: List<Pair<String, String>> = emptyList(),

    // 원본 데이터도 저장
    val rawColors: List<ColorItemDto> = emptyList(),

    val selectedColorIndex: Int? = null,
    val isUploading: Boolean = false
)

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val locationRepo: LocationRepository,
    private val uploadRepo: UploadRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(UploadState())
    val uiState = _uiState.asStateFlow()

    // 초기 분석
    fun initAnalysis(uri: Uri) {
        if (_uiState.value.capturedUri == uri && _uiState.value.draftId != null) return
        _uiState.value = _uiState.value.copy(capturedUri = uri)

        viewModelScope.launch {
            // 위치 가져오기
            val location = locationRepo.getCurrentLocation()
            val lat = location?.latitude ?: 37.55
            val lng = location?.longitude ?: 126.97
            val address = locationRepo.getAddressFromLocation(lat, lng)

            _uiState.value = _uiState.value.copy(
                detectedLocation = address,
                nearbyPlaces = listOf(PlaceData("[현재 위치]", address, lat, lng)),
                currentLat = lat,
                currentLng = lng
            )

            // 파일 변환
            val file = uriToFile(uri)

            if (file != null) {
                uploadRepo.capturePost(file, lat, lng).onSuccess { resultData ->

                    val uiColors = resultData.top4Colors.map { colorDto ->
                        val rawHex = colorDto.hexCode.trim()
                        val fixedHex = if (rawHex.startsWith("#")) rawHex else "#$rawHex"

                        val percentString = "${(colorDto.ratio * 100).toInt()}%"

                        fixedHex to percentString
                    }

                    _uiState.value = _uiState.value.copy(
                        draftId = resultData.draftId,
                        analyzedColors = uiColors,
                        rawColors = resultData.top4Colors,
                        selectedColorIndex = 0
                    )
                    Log.d("UploadVM", "색상 분석 완료: $uiColors")

                }.onFailure {
                    Log.e("UploadVM", "1차 전송 실패: ${it.message}")
                }
            } else {
                Log.e("UploadVM", "파일 변환 실패")
            }
        }
    }

    // 위치 텍스트 수동 변경
    fun updateLocation(newPlace: PlaceData) {
        _uiState.value = _uiState.value.copy(
            detectedLocation = newPlace.name,
            currentLat = newPlace.latitude,
            currentLng = newPlace.longitude
        )
    }

    // 위치 확정
    fun confirmLocationAndNext() {
        val state = _uiState.value
        if (state.draftId == null) return

        viewModelScope.launch {
            val result = uploadRepo.updateLocation(
                state.draftId,
                state.currentLat,
                state.currentLng,
                state.detectedLocation
            )

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(step = 1)
            } else {
                Log.e("UploadVM", "위치 업데이트 실패")
            }
        }
    }

    // 색상 선택
    fun selectColor(index: Int) {
        _uiState.value = _uiState.value.copy(selectedColorIndex = index)
    }

    // 색상 확정
    fun confirmColorAndNext() {
        val state = _uiState.value
        val selectedIndex = state.selectedColorIndex ?: return
        if (state.draftId == null) return

        viewModelScope.launch {
            // [중요] UI 문자열 말고, 아까 저장해둔 rawColors에서 진짜 ID를 꺼냅니다!
            val colorId = state.rawColors.getOrNull(selectedIndex)?.colorId ?: 0

            val result = uploadRepo.updateMainColor(
                state.draftId,
                colorId
            )

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(step = 2)
            } else {
                Log.e("UploadVM", "색상 업데이트 실패")
            }
        }
    }

    // 최종 업로드
    fun uploadPost() {
        val state = _uiState.value
        if (state.draftId == null) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUploading = true)

            val result = uploadRepo.publishPost(state.draftId)

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(isUploading = false, step = 3)
            } else {
                Log.e("UploadVM", "최종 발행 실패")
                _uiState.value = _uiState.value.copy(isUploading = false)
            }
        }
    }

    // 장소 검색
    fun searchPlaces(query: String) {
        if (query.isBlank()) return

        // Places SDK 초기화
        if (!Places.isInitialized()) {
            Places.initialize(context, BuildConfig.PLACES_API_KEY)
        }
        val placesClient = Places.createClient(context)

        // 검색 요청
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .setCountries("KR")
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                val places = response.autocompletePredictions.map { prediction ->
                    PlaceData(
                        name = prediction.getPrimaryText(null).toString(), // 장소명
                        address = prediction.getSecondaryText(null).toString(), // 주소
                        latitude = 0.0,
                        longitude = 0.0,
                        placeId = prediction.placeId
                    )
                }
                _uiState.value = _uiState.value.copy(nearbyPlaces = places)
            }
            .addOnFailureListener {
                Log.e("UploadVM", "검색 실패: ${it.message}")
            }
    }

    // 장소 선택
    fun selectPlace(place: PlaceData) {
        if (place.placeId == null) {
            updateLocation(place)
            _uiState.value = _uiState.value.copy(nearbyPlaces = emptyList())
            return
        }

        // ID가 있으면 좌표 조회
        if (!Places.isInitialized()) {
            Places.initialize(context, BuildConfig.PLACES_API_KEY)
        }
        val placesClient = Places.createClient(context)

        val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(place.placeId, placeFields)

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val item = response.place
                val lat = item.latLng?.latitude ?: 0.0
                val lng = item.latLng?.longitude ?: 0.0

                val finalPlace = place.copy(
                    latitude = lat,
                    longitude = lng,
                    address = item.address ?: place.address
                )

                updateLocation(finalPlace)
                _uiState.value = _uiState.value.copy(nearbyPlaces = emptyList()) // 리스트 닫기
                Log.d("UploadVM", "장소 확정: ${finalPlace.name} ($lat, $lng)")
            }
            .addOnFailureListener {
                Log.e("UploadVM", "좌표 조회 실패", it)
                updateLocation(place)
                _uiState.value = _uiState.value.copy(nearbyPlaces = emptyList())
            }
    }


    // 유틸: Uri -> File 변환
    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("upload_img", ".jpg", context.cacheDir)
            FileOutputStream(tempFile).use { output ->
                inputStream.copyTo(output)
            }
            tempFile
        } catch (e: Exception) {
            Log.e("UploadVM", "파일 생성 에러", e)
            null
        }
    }
}