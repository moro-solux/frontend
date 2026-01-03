package com.solux.moro.ui.camera

import android.net.Uri
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.solux.moro.R
import com.solux.moro.core.util.figmaDp
//import coil.compose.AsyncImage
@Composable
fun CameraLayout(
    showShotCount: Boolean = false,
    onCameraClick: () -> Unit,
    showConfirmDialog: Boolean,
    onConfirm: () -> Unit,
    onRetry: () -> Unit,
    capturedImageUri: Uri? = null, //찍은 사진 확인용
    cameraContent: @Composable () -> Unit //카메라 화면 끼워넣기
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        cameraContent()

        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            CameraTopBar()
        }

        if (showShotCount) {
            ShotCount(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = figmaDp(70f) + figmaDp(12f),
                        end = figmaDp(16f)
                    )
            )
        }


        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            CameraBottomBar(
                onCameraClick = onCameraClick
            )
        }

        if (showConfirmDialog) {
            ConfirmPhotoDialog(
                imageUri = capturedImageUri,
                onConfirm = onConfirm,
                onRetry = onRetry
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
fun ConfirmPhotoDialog(
    imageUri: Uri?, // 찍은 사진 주소
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
            // 사진이 들어갈 박스
            Box(
                modifier = Modifier
                    .width(230.dp)
                    .height(307.dp)
                    .background(
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri, // 사진 파일 주소
                        contentDescription = "찍은 사진",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

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