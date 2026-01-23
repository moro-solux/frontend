package com.solux.moro.ui.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MyMissionScreen(
    navController: NavHostController,
    viewModel: MissionViewModel = hiltViewModel()
) {
    val myMissions by viewModel.myMissions.collectAsState()
    val todayDate = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }

    Scaffold(
        topBar = {
            TopBarBack(
                title = "My Mission",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .background(Color(0xFF121212))
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(figmaDp(14f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = figmaDp(20f))
        ) {
            if (myMissions.isEmpty()) {
                item {
                    Text("아직 참여한 미션이 없습니다.", color = Color.Gray, modifier = Modifier.padding(20.dp))
                }
            }

            items(myMissions) { post ->
                val isToday = post.createdAt.startsWith(todayDate)

                if (isToday) {
                    MyMission(
                        title = post.missionTitle,
                        imageUrl = post.imageUrl,
                        shotAt = "촬영일시: ${formatDateSimple(post.createdAt)}",
                        onClick = { navController.navigate("mission_post/${post.misPostId}") }
                    )
                } else {
                    F_MyMission(
                        title = post.missionTitle,
                        imageUrl = post.imageUrl,
                        shotAt = "촬영일시: ${formatDateSimple(post.createdAt)}",
                        onClick = { navController.navigate("mission_post/${post.misPostId}") }
                    )
                }
            }
        }
    }
}

@Composable
fun MyMission(
    title: String,
    imageUrl: String,
    shotAt: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .border(width = figmaDp(1f), color = Color(0xFF262626), shape = RoundedCornerShape(size = figmaDp(16f)))
            .fillMaxWidth(0.9f)
            .height(figmaDp(100f))
            .background(color = Color(0xFF171717), shape = RoundedCornerShape(size = figmaDp(16f)))
            .clip(RoundedCornerShape(size = figmaDp(16f)))
            .clickable { onClick() }
            .padding(start = figmaDp(16f), top = figmaDp(16f), end = figmaDp(16f), bottom = figmaDp(16f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(figmaDp(68f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Start),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .width(figmaDp(64f)).height(figmaDp(64f))
                    .background(color = Color(0xFF404040), shape = RoundedCornerShape(size = figmaDp(12f)))
                    .clip(RoundedCornerShape(size = figmaDp(12f)))
            ) {
                AsyncImage(model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.width(figmaDp(8f)).height(figmaDp(8f)).background(color = Color(0xFFF2F2F2), shape = RoundedCornerShape(size = figmaDp(9999f))))
                    Text(
                        text = "진행중",
                        style = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight(400), color = Color(0xFFF2F2F2))
                    )
                }
                Row(modifier = Modifier.height(figmaDp(20f)), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight(400), color = Color(0xFFFFFFFF))
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = shotAt, style = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight(400), color = Color(0xFFF2F2F2)))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.chevron_right), contentDescription = "active arrow")
        }
    }
}


@Composable
fun F_MyMission(
    title: String,
    imageUrl: String,
    shotAt: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .border(width = figmaDp(1f), color = Color(0xFF262626), shape = RoundedCornerShape(size = figmaDp(16f)))
            .fillMaxWidth(0.9f)
            .height(figmaDp(100f))
            .background(color = Color(0xFF171717), shape = RoundedCornerShape(size = figmaDp(16f)))
            .clip(RoundedCornerShape(size = figmaDp(16f)))
            .clickable { onClick() }
            .padding(start = figmaDp(16f), top = figmaDp(16f), end = figmaDp(16f), bottom = figmaDp(16f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(figmaDp(68f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Start),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .width(figmaDp(64f)).height(figmaDp(64f))
                    .background(color = Color(0xFF404040), shape = RoundedCornerShape(size = figmaDp(12f)))
                    .clip(RoundedCornerShape(size = figmaDp(12f)))
            ) {
                AsyncImage(model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(figmaDp(8f), Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.width(figmaDp(8f)).height(figmaDp(8f)).background(color = Color(0xFFA5A5A5), shape = RoundedCornerShape(size = figmaDp(9999f))))
                    Text(
                        text = "완료",
                        style = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight(400), color = Color(0xFFA5A5A5))
                    )
                }
                Row(modifier = Modifier.height(figmaDp(20f)), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight(400), color = Color(0xFFA5A5A5)) 
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = shotAt, style = TextStyle(fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight(400), color = Color(0xFFA5A5A5)))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.chevron_right_disabled), contentDescription = "inactive arrow")
        }
    }
}

fun formatDateSimple(dateString: String): String {
    return try {
        dateString.split("T")[0].replace("-", ".")
    } catch (e: Exception) {
        dateString
    }
}