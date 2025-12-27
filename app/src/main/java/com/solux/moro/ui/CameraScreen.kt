package com.solux.moro.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.solux.moro.R
import com.solux.moro.core.util.figmaDp

@Composable
fun CameraScreen() {

    var showConfirmDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF404040)) // 임시
        )

        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            CameraTopBar()
        }

        ShotCount(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = figmaDp(70f) + figmaDp(12f),
                    end = figmaDp(16f)
                )
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            CameraBottomBar(
                onCameraClick = {
                    showConfirmDialog = true
                }
            )
        }

        if (showConfirmDialog) {
            ConfirmPhotoDialog(
                onConfirm = {
                    showConfirmDialog = false
                    // TODO: 업로드 로직
                },
                onRetry = {
                    showConfirmDialog = false
                }
            )
        }
    }
}

@Composable
fun CameraTopBar() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(70f))
                .background(Color(0xFF121212))
                .padding(
                    start = figmaDp(16f),
                    top = figmaDp(5f),
                    end = figmaDp(16f),
                    bottom = figmaDp(5f)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.left),
                contentDescription = null
            )

            Image(
                painter = painterResource(id = R.drawable.camera_switch),
                contentDescription = null
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(1f))
                .background(Color(0xFFF2F2F2))
        )
    }
}

@Composable
fun ShotCount(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(figmaDp(50f))
            .background(
                color = Color(0xFF121212),
                shape = RoundedCornerShape(figmaDp(50f))
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "3",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun CameraBottomBar(
    onCameraClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(figmaDp(285f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(21f)),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(22f)),
            text = "Capture your mission moment",
            fontSize = 14.sp,
            lineHeight = 19.6.sp,
            fontWeight = FontWeight(600),
            color = Color(0xFFA3A3A3),
            textAlign = TextAlign.Center
        )

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(figmaDp(1f))
                    .background(Color(0xFFF2F2F2))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(figmaDp(242f))
                    .background(Color(0xFF121212))
                    .padding(
                        start = figmaDp(16f),
                        top = figmaDp(58f),
                        end = figmaDp(16f),
                        bottom = figmaDp(58f)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(figmaDp(70f))
                        .border(
                            width = figmaDp(4f),
                            color = Color.White,
                            shape = RoundedCornerShape(figmaDp(9999f))
                        )
                        .clickable { onCameraClick() }
                )
            }
        }
    }
}

@Composable
fun ConfirmPhotoDialog(
    onConfirm: () -> Unit,
    onRetry: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .width(figmaDp(309f))
                .height(figmaDp(500f))
                .background(
                    color = Color(0xFF121212),
                    shape = RoundedCornerShape(figmaDp(20f))
                ),
            verticalArrangement = Arrangement.spacedBy(
                figmaDp(37f),
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .width(230.dp)
                    .height(307.dp)
                    .background(
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
            )

            Text(
                text = "이 사진을 업로드 하시겠습니까?",
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(figmaDp(16f))
            ) {
                Box(
                    modifier = Modifier
                        .width(figmaDp(85f))
                        .height(figmaDp(35f))
                        .background(
                            color = Color(0xFFF2F2F2),
                            shape = RoundedCornerShape(figmaDp(10f))
                        )
                        .clickable { onConfirm() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "예",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF121212)
                    )
                }

                Box(
                    modifier = Modifier
                        .width(figmaDp(85f))
                        .height(figmaDp(35f))
                        .border(
                            width = figmaDp(1f),
                            color = Color(0xFFA5A5A5),
                            shape = RoundedCornerShape(figmaDp(10f))
                        )
                        .clickable { onRetry() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "다시 찍기",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFF2F2F2)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CameraScreenPreview() {
    CameraScreen()
}
