package com.solux.moro.ui.camera

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp
import com.solux.moro.ui.mission.InstagramUpload
import com.solux.moro.ui.mission.Upload_Button


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPostScreen() {
    var step by remember { mutableStateOf(0) }
    var selectedColorIndex by remember { mutableStateOf<Int?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

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
                .padding(start = figmaDp(16f), top = figmaDp(70f), end = figmaDp(16f), bottom = figmaDp(70f)),
            verticalArrangement = Arrangement.spacedBy(figmaDp(30f), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //컬러 수집 문구
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(figmaDp(28f)),
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UploadStepTitle(step)
            }
            // 위치
            Post_Place(
                onClick = {
                    showBottomSheet = true
                }
            )
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    containerColor = Color.Transparent, // 네 디자인 유지
                    dragHandle = null                   // 상단 핸들 제거
                ) {
                    LocationBottomSheet(onClose = { showBottomSheet = false })
                }
            }

            // 게시물
            UploadPostFrame(
                step = step,
                selectedColorIndex = selectedColorIndex,
                onColorSelected = { selectedColorIndex = it }
            )


            // 버튼
            Row(
                horizontalArrangement = Arrangement.spacedBy(figmaDp(8f)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UploadBottomAction(
                    step = step,
                    onNext = { step++ },
                    onUpload = { step = 3 },
                    onInstagram = {
                        // TODO: 인스타 공유
                    }
                )
            }

        }
    }
}

@Composable
fun UploadStepTitle(step: Int) {
    Text(
        text = when (step) {
            0 -> "이 사진의 컬러를 수집할까요?"
            1 -> "대표색상을 선택해주세요."
            else -> "Moro에 업로드하면 나의 색상 여정이 지도에 그려집니다."
        },
        style = when (step) {
            2 -> TextStyle(
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFFBDBDBD),
                textAlign = TextAlign.Center,
            )

            else -> TextStyle(
                fontSize = 18.sp,
                lineHeight = 28.sp,
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
            Upload_Button()
        }

        3 -> {
            InstagramUpload()
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
fun Post_Place(onClick: () -> Unit) {
    Row(
        modifier = Modifier .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(figmaDp(10f), Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .width(figmaDp(16.36364f))
                .height(figmaDp(20f)),
            painter = painterResource(id = R.drawable.map),
            contentDescription = "image description"
        )
        Text(
            text = "숙명여자대학교 Sookmyung women",
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 28.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )

        Row(
            modifier = Modifier
                .width(figmaDp(9.14158f))
                .height(figmaDp(16f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(10.445481300354004f), Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.chevron_right),
                contentDescription = "image description",
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
    selectedColorIndex: Int?,
    onColorSelected: (Int) -> Unit
) {
    val colors = listOf(
        "#3357FF" to "42%",
        "#FF5733" to "28%",
        "#33FF57" to "18%",
        "#F3FF33" to "12%"
    )

    Row(
        modifier = Modifier
            .border(
                width = figmaDp(1f),
                color = Color(0xFFA5A5A5),
                shape = RoundedCornerShape(figmaDp(18.3f))
            )
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
        )

        Column(
            modifier = Modifier
                .width(figmaDp(71.32f))
                .height(figmaDp(313.46f)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            colors.forEachIndexed { index, (hex, percent) ->
                UploadPost_Color(
                    colorHex = hex,
                    percent = percent,
                    isSelected = selectedColorIndex == index,
                    isSelectable = step == 1,
                    onClick = { onColorSelected(index) }
                )

            }
        }
    }
}


@Preview
@Composable
fun UploadPreview() {
    UploadPostScreen()
}