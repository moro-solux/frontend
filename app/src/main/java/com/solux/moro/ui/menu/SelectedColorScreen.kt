package com.solux.moro.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp
// 최신 DTO 이름 Import
import com.solux.moro.data.dto.response.ColorPostDto

@Composable
fun SelectedColorScreen(
    colorId: Long,
    hexCode: String,
    viewModel: SelectedColorViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val posts by viewModel.posts.collectAsState()

    // Hex 코드 처리
    val safeHex = if (hexCode.startsWith("#")) hexCode else "#$hexCode"
    val displayColor = try {
        Color(android.graphics.Color.parseColor(safeHex))
    } catch (e: Exception) {
        Color.Gray // 예외 발생 시 기본 색상
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadColorPosts(colorId)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            TopBarBack("컬러맵", onBackClick = { navController.popBackStack() })
        },
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(figmaDp(14f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SelectedColorSection(hexCode, displayColor)
            }

            val rows = posts.chunked(3)
            items(rows) { rowPosts ->
                ImageRow(
                    rowPosts = rowPosts,
                    onPostClick = { postId ->
                        // 클릭 시 상세 화면으로 이동
                        navController.navigate("color_post/$colorId/$postId")
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(figmaDp(30f))) }
        }
    }
}

@Composable
fun SelectedColorSection(hexCode: String, displayColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(figmaDp(169f)),
        horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .width(figmaDp(80f))
                .height(figmaDp(114f)),
            verticalArrangement = Arrangement.spacedBy(figmaDp(9f), Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .shadow(
                        elevation = figmaDp(20f),
                        spotColor = displayColor.copy(alpha = 0.4f),
                        ambientColor = displayColor.copy(alpha = 0.4f)
                    )
                    .border(
                        width = figmaDp(2f),
                        color = Color(0x4DFFFFFF),
                        shape = RoundedCornerShape(size = figmaDp(9999f))
                    )
                    .size(figmaDp(80f))
                    .background(color = displayColor, shape = RoundedCornerShape(size = figmaDp(9999f)))
            )
            Text(
                text = "#${hexCode.replace("#","").uppercase()}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = displayColor,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun ImageRow(
    rowPosts: List<ColorPostDto>,
    onPostClick: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = figmaDp(16f)),
        horizontalArrangement = Arrangement.spacedBy(figmaDp(5.5f), Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        rowPosts.forEach { post ->
            ImageItem(
                imageUrl = post.imageUrl,
                onClick = { onPostClick(post.postId) }
            )
        }

        repeat(3 - rowPosts.size) {
            Box(modifier = Modifier.weight(1f).aspectRatio(1f))
        }
    }
}

@Composable
fun ImageItem(
    imageUrl: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(figmaDp(110.66666f))
            .clip(RoundedCornerShape(figmaDp(9.22222f)))
            .background(Color(0xFF2A2A2A))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}