package com.solux.moro.ui.camera

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp
import com.solux.moro.ui.mission.component.Upload_Button
import com.solux.moro.ui.viewmodel.UploadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPostScreen(
    capturedUri: Uri,
    viewModel: UploadViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(capturedUri) {
        viewModel.initAnalysis(capturedUri)
    }

    Scaffold(
        topBar = { TopBarBack("게시물 업로드") },
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color(0xFF121212))
                .padding(innerPadding)
                .padding(start = figmaDp(16f), top = figmaDp(70f), end = figmaDp(16f), bottom = figmaDp(30f)),
            verticalArrangement = Arrangement.spacedBy(figmaDp(30f), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(figmaDp(28f)),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UploadStepTitle(state.step)
            }

            Post_Place(
                locationName = state.detectedLocation,
                isClickable = (state.step == 0),
                showActiveColor = (state.step == 3),
                onClick = { showBottomSheet = true }
            )

            // 바텀 시트 (위치 변경용)
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    containerColor = Color.Transparent, // 투명하게 해서 디자인 적용
                    dragHandle = null
                ) {
                    LocationBottomSheet(
                        onClose = { showBottomSheet = false },

                        // [연결 1] 뷰모델의 검색 함수 연결
                        onSearch = { query ->
                            viewModel.searchPlaces(query)
                        },

                        // [연결 2] 뷰모델의 장소 선택 함수 연결
                        onPlaceSelected = { selectedPlace ->
                            viewModel.selectPlace(selectedPlace)
                            showBottomSheet = false
                        },

                        selectedLocation = state.detectedLocation,
                        nearbyPlaces = state.nearbyPlaces
                    )
                }
            }

            UploadPostFrame(
                step = state.step,
                imageUri = state.capturedUri,
                colors = state.analyzedColors,
                selectedColorIndex = state.selectedColorIndex,
                onColorSelected = { viewModel.selectColor(it) }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UploadBottomAction(
                    step = state.step,
                    isNextEnabled = !state.isUploading && (if (state.step == 1) state.selectedColorIndex != null else true),
                    onNext = {
                        when (state.step) {
                            0 -> viewModel.confirmLocationAndNext()
                            1 -> viewModel.confirmColorAndNext()
                        }
                    },
                    onUpload = { viewModel.uploadPost() },
                    onInstagram = { /* 인스타 로직 */ }
                )
            }
        }
    }
}

// 바텀시트 내용
@Composable
fun LocationBottomSheet(
    viewModel: UploadViewModel,
    onDismiss: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 300.dp)
    ) {
        Text("장소 찾기", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("장소명 검색") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.searchPlaces(searchQuery) }) {
                Text("검색")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(uiState.nearbyPlaces) { place ->
                ListItem(
                    headlineContent = { Text(place.name, fontWeight = FontWeight.Bold) },
                    supportingContent = { Text(place.address) },
                    modifier = Modifier.clickable {
                        viewModel.selectPlace(place)
                        onDismiss()
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun UploadPost_Color(
    colorHex: String,
    percent: String,
    isSelected: Boolean,
    isSelectable: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = when {
            !isSelectable -> 1f
            isSelected -> 75.5f / 71.32f
            else -> 68f / 71.32f
        },
        label = "colorScale"
    )


    val boxColor = try {
        Color(android.graphics.Color.parseColor(colorHex))
    } catch (e: Exception) {
        Color.Gray // 파싱 실패 시 기본색
    }

    Column(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .width(figmaDp(71.32f))
            .height(figmaDp(71.32f))
            .background(
                color = boxColor,
                shape = RoundedCornerShape(figmaDp(3.5f))
            )
            .clickable(enabled = isSelectable) { onClick() }
            .padding(figmaDp(5f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(0f), Alignment.Bottom),
        horizontalAlignment = Alignment.Start,
    ) {
        // 색상 코드
        Text(
            text = colorHex,
            style = TextStyle(
                fontSize = 12.sp,
                color = if (boxColor.luminance() > 0.5f) Color.Black else Color.White,

                lineHeight = 13.sp,
                platformStyle = androidx.compose.ui.text.PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )

        // 퍼센트
        Text(
            text = percent,
            style = TextStyle(
                fontSize = 12.sp,
                color = if (boxColor.luminance() > 0.5f) Color.Black else Color.White,
                lineHeight = 13.sp,
                platformStyle = androidx.compose.ui.text.PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}



@Composable
fun UploadStepTitle(step: Int) {
    Text(
        text = when (step) {
            0 -> "이 사진의 컬러를 수집할까요?"
            1 -> "대표색상을 선택해주세요."
            2 -> "Moro에 업로드하면 나의 색상 여정이 지도에 그려집니다."
            else -> "업로드가 완료되었습니다!"
        },
        style = when (step) {
            2 -> TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFFBDBDBD),
                textAlign = TextAlign.Center,
            )
            else -> TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
    )
}

@Composable
fun UploadBottomAction(
    step: Int,
    isNextEnabled: Boolean = true,
    onNext: () -> Unit,
    onUpload: () -> Unit,
    onInstagram: () -> Unit
) {
    when (step) {
        0, 1 -> {
            Next_Button(
                text = "다음",
                enabled = isNextEnabled,
                onClick = onNext
            )
        }
        2 -> {
            Upload_Button(onClick = onUpload)
        }
        3 -> {
        }
    }
}

@Composable
fun Next_Button(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(figmaDp(90f))
            .height(figmaDp(45f))
            .background(
                color = if (enabled) Color(0xFFF2F2F2) else Color(0xFFBDBDBD),
                shape = RoundedCornerShape(figmaDp(8f))
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(vertical = figmaDp(8f)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            lineHeight = 22.4.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun Post_Place(
    locationName: String,
    showActiveColor: Boolean = false,
    isClickable: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isClickable || showActiveColor) Color.White else Color.Gray
    val iconAlpha = if (isClickable || showActiveColor) 1f else 0.4f

    Row(
        modifier = Modifier
            .clickable(enabled = isClickable) { onClick() },
        horizontalArrangement = Arrangement.spacedBy(figmaDp(10f), Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.width(figmaDp(16.36f)).height(figmaDp(20f)),
            painter = painterResource(id = R.drawable.map),
            contentDescription = "map",
            alpha = iconAlpha
        )
        Text(
            text = locationName,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                color = textColor,
                textAlign = TextAlign.Center,
            )
        )
        if (isClickable) {
            Image(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = "edit",
            )
        }
    }
}

@Composable
fun UploadPostFrame(
    step: Int,
    imageUri: Uri?,
    colors: List<Pair<String, String>>,
    selectedColorIndex: Int?,
    onColorSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .border(width = figmaDp(1f), color = Color(0xFFA5A5A5), shape = RoundedCornerShape(figmaDp(18.3f)))
            .width(figmaDp(343f))
            .height(figmaDp(342.7f))
            .padding(figmaDp(14f)),
        horizontalArrangement = Arrangement.spacedBy(figmaDp(7.3f)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(figmaDp(235f))
                .height(figmaDp(313f))
                .background(Color.DarkGray, RoundedCornerShape(figmaDp(9.14f)))
                .clip(RoundedCornerShape(figmaDp(9.14f)))
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Column(
            modifier = Modifier.width(figmaDp(71.32f)).height(figmaDp(313.46f)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            colors.forEachIndexed { index, (hex, percent) ->
                UploadPost_Color(
                    colorHex = hex,
                    percent = percent,
                    isSelected = selectedColorIndex == index,
                    isSelectable = (step == 1),
                    onClick = { onColorSelected(index) }
                )
            }
        }
    }
}