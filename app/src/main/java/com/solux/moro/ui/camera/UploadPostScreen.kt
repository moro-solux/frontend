package com.solux.moro.ui.camera

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp
import com.solux.moro.ui.mission.InstagramUpload
import com.solux.moro.ui.mission.Upload_Button
import com.solux.moro.ui.viewmodel.UploadViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPostScreen(
    capturedUri: Uri,
    viewModel: UploadViewModel = viewModel(),
    onNavigateHome: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

    // 진입 시 분석 시작
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
            // 1. 단계별 멘트
            Row(
                modifier = Modifier.fillMaxWidth().height(figmaDp(28f)),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UploadStepTitle(state.step)
            }

            // 2. 위치 (Step 0에서만 클릭 가능하도록 제어)
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
                    containerColor = Color.Transparent,
                    dragHandle = null
                ) {
                    LocationBottomSheet(
                        onClose = { showBottomSheet = false },
                        onPlaceSelected = { newPlace ->
                            viewModel.updateLocation(newPlace) // 뷰모델에 선택한 장소 알림
                            showBottomSheet = false
                        },
                        selectedLocation = state.detectedLocation // 현재 선택된 장소 이름을 넘겨줌
                    )
                }
            }

            // 3. 게시물 프레임 (Step 1에서만 색상 선택 가능)
            UploadPostFrame(
                step = state.step,
                imageUri = state.capturedUri,
                colors = state.analyzedColors,
                selectedColorIndex = state.selectedColorIndex,
                onColorSelected = { viewModel.selectColor(it) }
            )

            // 4. 하단 버튼 (Step에 따라 '다음' / '업로드' / '완료' 변경)
            Row(
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UploadBottomAction(
                    step = state.step,
                    // Step 1일 때는 색상을 골라야만 '다음' 버튼 활성화
                    isNextEnabled = if (state.step == 1) state.selectedColorIndex != null else true,
                    onNext = { viewModel.nextStep() },
                    onUpload = { viewModel.uploadPost() },
                    onInstagram = { /* 인스타 로직 */ }
                )
            }
        }
    }
}
@Composable
fun UploadStepTitle(step: Int) {
    Text(
        text = when (step) {
            0 -> "이 사진의 컬러를 수집할까요?"     // Step 0: 위치 확인 단계
            1 -> "대표색상을 선택해주세요."       // Step 1: 색상 선택 단계
            2 -> "Moro에 업로드하면 나의 색상 여정이 지도에 그려집니다." // Step 2: 업로드 준비
            else -> "업로드가 완료되었습니다!"    // Step 3: 완료
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
                onClick = onNext
            )
        }


        2 -> {
            Upload_Button(onClick = onUpload)
        }

        3 -> {
            InstagramUpload(
                onInstagramClick = onInstagram,
                onSaveClick = { /* 나중에 저장 기능 구현하면 여기에 연결 */ }
            )
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
    isClickable: Boolean, // 클릭 가능 여부 파라미터 추가
    onClick: () -> Unit
) {
    // 클릭이 가능하거나(Step 0) OR 활성화 색상을 보여달라고 하면(Step 3) -> 흰색
    // 그 외에는 -> 회색
    val textColor = if (isClickable || showActiveColor) Color.White else Color.Gray
    val iconAlpha = if (isClickable || showActiveColor) 1f else 0.4f

    Row(
        modifier = Modifier
            .clickable(enabled = isClickable) { onClick() }, // Step 0 아니면 클릭 불가
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
                color = textColor, // 상태에 따라 색상 변경
                textAlign = TextAlign.Center,
            )
        )
        if (isClickable) { // 수정 가능할 때만 화살표 보이기
            Image(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = "edit",
            )
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
            !isSelectable -> 1f                // step 0, 2 → 디자인 유지
            isSelected -> 75.5f / 71.32f       // 선택됨
            else -> 68f / 71.32f               // 선택 안됨
        },
        label = "colorScale"
    )

    Column(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .width(figmaDp(71.32f))
            .height(figmaDp(71.32f))
            .background(
                color = Color(0xFF4982E5),
                shape = RoundedCornerShape(figmaDp(3.5f))
            )
            .clickable(enabled = isSelectable) { onClick() }
            .padding(figmaDp(5f)),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = colorHex,
            fontSize = 12.sp,
            color = Color.Black
        )

        Text(
            text = percent,
            fontSize = 12.sp,
            color = Color.White
        )
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
        // 사진 (왼쪽)
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

        // 색상 리스트 (오른쪽)
        Column(
            modifier = Modifier.width(figmaDp(71.32f)).height(figmaDp(313.46f)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            colors.forEachIndexed { index, (hex, percent) ->
                UploadPost_Color(
                    colorHex = hex,
                    percent = percent,
                    isSelected = selectedColorIndex == index,
                    isSelectable = (step == 1), // Step 1에서만 선택 가능!
                    onClick = { onColorSelected(index) }
                )
            }
        }
    }
}
