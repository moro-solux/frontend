package com.solux.moro.ui.menu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp
import com.solux.moro.data.dto.response.ColorCandidateDto
import com.solux.moro.ui.camera.Next_Button

@Composable
fun ColorMapEditScreen(
    postId: Long,
    navController: NavHostController,
    viewModel: EditPostViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // 화면 진입 시 데이터 로드
    LaunchedEffect(postId) {
        viewModel.loadPostData(0L, postId)
    }

    Scaffold(
        topBar = {
            TopBarBack("게시물 수정", onBackClick = {
                navController.popBackStack()
            })
        },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF121212))
                .padding(innerPadding)
                .padding(start = figmaDp(16f), top = figmaDp(70f), end = figmaDp(16f), bottom = figmaDp(30f)),
            verticalArrangement = Arrangement.spacedBy(figmaDp(30f), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 1. 타이틀
            Text(
                text = "대표색상을 선택해주세요.",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            )



            // 2. 메인 프레임
            EditPostFrame(
                imageUrl = state.imageUrl,
                colors = state.analyzedColors,
                selectedColorIndex = state.selectedColorIndex,
                onColorSelected = { index -> viewModel.selectColor(index) }
            )

            // 3. 하단 버튼
            Row(
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Next_Button(
                    text = "수정",
                    enabled = state.selectedColorIndex != null && !state.isUpdating,
                    onClick = {
                        viewModel.updatePost {
                            navController.popBackStack()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EditPostFrame(
    imageUrl: String,
    colors: List<ColorCandidateDto>,
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
        // 이미지 영역
        Box(
            modifier = Modifier
                .width(figmaDp(235f))
                .height(figmaDp(313f))
                .background(Color.DarkGray, RoundedCornerShape(figmaDp(9.14f)))
                .clip(RoundedCornerShape(figmaDp(9.14f)))
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // 색상 리스트 영역
        Column(
            modifier = Modifier
                .width(figmaDp(71.32f))
                .height(figmaDp(313.46f)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            colors.forEachIndexed { index, colorItem ->
                val hex = if (colorItem.hexCode.startsWith("#")) colorItem.hexCode else "#${colorItem.hexCode}"

                EditPost_ColorItem(
                    colorHex = hex,
                    percent = "",
                    isSelected = selectedColorIndex == index,
                    isSelectable = true,
                    onClick = { onColorSelected(index) }
                )
            }
        }
    }
}

@Composable
fun EditPost_ColorItem(
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
        Color.Gray
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

        if (percent.isNotEmpty()) {
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
}