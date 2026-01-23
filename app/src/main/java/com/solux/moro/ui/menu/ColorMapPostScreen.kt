package com.solux.moro.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp
import com.solux.moro.data.dto.response.ColorCandidateDto

@Composable
fun ColorMapPostScreen(
    colorId: Long,
    postId: Long,
    viewModel: EditPostViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(postId) {
        viewModel.loadPostData(colorId, postId)
    }

    Scaffold(
        topBar = {
            TopBarBack("게시물", onBackClick = {
                navController.popBackStack()
            })
        },
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(innerPadding)
        ) {
            if (state.username.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        ColorPost(
                            userName = state.username,
                            imageUrl = state.imageUrl,
                            colorCandidates = state.colorCandidates,
                            onEditClick = {
                                navController.navigate("postEditScreen/$postId")
                            },
                            onDeleteClick = {
                                viewModel.deletePost(postId) {
                                    navController.popBackStack()
                                }
                            }
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ColorPost(
    userName: String,
    imageUrl: String,
    colorCandidates: List<ColorCandidateDto>,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(figmaDp(375f))
            .padding(figmaDp(16f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(10.5769f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        // 1. 닉네임 및 메뉴 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(38.46154f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(10f), Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(figmaDp(38.46154f))
                    .clip(CircleShape)
                    .background(Color(0xFFF2F2F2))
            )

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "@$userName",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFF2F2F2),
                    )
                )

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "더 보기",
                            tint = Color.White,
                            modifier = Modifier.size(figmaDp(24f))
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(figmaDp(142f))
                            .background(Color(0xE5121212), RoundedCornerShape(figmaDp(4f)))
                            .border(0.5.dp, Color(0x33FFFFFF), RoundedCornerShape(figmaDp(4f)))
                    ) {
                        ColorPostMenuContent(
                            onEdit = {
                                expanded = false
                                onEditClick()
                            },
                            onDelete = {
                                expanded = false
                                onDeleteClick()
                            }
                        )
                    }
                }
            }
        }

        // 2. 피드 이미지 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(363f))
                .padding(vertical = figmaDp(10f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .width(figmaDp(257f))
                    .height(figmaDp(343f))
                    .clip(RoundedCornerShape(figmaDp(10f)))
                    .background(Color(0xFF1E1E1E))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .width(figmaDp(78f))
                    .height(figmaDp(343f)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                colorCandidates.take(4).forEach { candidate ->
                    // Hex 코드 # 처리
                    val hex = if (candidate.hexCode.startsWith("#")) candidate.hexCode else "#${candidate.hexCode}"
                    Box(
                        modifier = Modifier
                            .size(figmaDp(78f))
                            .background(
                                color = Color(android.graphics.Color.parseColor(hex)),
                                shape = RoundedCornerShape(figmaDp(3.846f))
                            )
                    )
                }
            }
        }

        // 3. 색상코드 영역
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(7.69f), Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            colorCandidates.forEach { candidate ->
                Text(
                    text = "#${candidate.hexCode.uppercase()} ",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA5A5A5),
                    )
                )
            }
        }
    }
}

@Composable
fun ColorPostMenuContent(onEdit: () -> Unit, onDelete: () -> Unit) {
    Column(
        modifier = Modifier.padding(vertical = figmaDp(5f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(5f))
    ) {
        // 수정 항목
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(28f)) // 기존 figmaDp(28.dp)에서 .dp 제거하여 통일
                .clickable { onEdit() }
                .padding(horizontal = figmaDp(15f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(15f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_white),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(figmaDp(18f))
            )
            Text(
                text = "수정",
                style = TextStyle(fontSize = 16.sp, color = Color(0xFFF2F2F2))
            )
        }

        // 삭제 항목
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(28f))
                .clickable { onDelete() }
                .padding(horizontal = figmaDp(15f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(15f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_trash_white),
                contentDescription = null,
                tint = Color(0xFFEA4335),
                modifier = Modifier.size(figmaDp(18f))
            )
            Text(
                text = "삭제",
                style = TextStyle(fontSize = 16.sp, color = Color(0xFFEA4335))
            )
        }
    }
}