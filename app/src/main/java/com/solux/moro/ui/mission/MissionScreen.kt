package com.solux.moro.ui.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBar2
import com.solux.moro.core.util.figmaDp
import com.solux.moro.data.dto.response.CurrentMissionDto
import com.solux.moro.data.dto.response.MissionPostDto
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun MissionScreen(
    navController: NavHostController? = null,
    viewModel: MissionViewModel = hiltViewModel()
) {
    val currentMission by viewModel.currentMission.collectAsState()
    val missionFeed by viewModel.missionFeed.collectAsState()
    val todaySubmission by viewModel.todaySubmission.collectAsState()
    val currentFilter by viewModel.currentFilter.collectAsState()


    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadMissionData()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = { TopBar2() },
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
            item {
                MissionSection(
                    mission = currentMission,
                    submission = todaySubmission,
                    onTakeMissionClick = {
                        currentMission?.let {
                            navController?.navigate("mission_camera/${it.missionId}")
                        }
                    },
                    onMyMissionClick = { navController?.navigate("my_mission") }
                )
            }

            item {
                FeedSection(
                    feedList = missionFeed,
                    currentFilter = currentFilter,
                    onFilterChange = { newFilter -> viewModel.changeFilter(newFilter) },
                    onPostClick = { postId -> navController?.navigate("mission_post/$postId") }
                )
            }
        }
    }
}


@Composable
fun MissionSection(
    mission: CurrentMissionDto?,
    submission: MissionPostDto?,
    onTakeMissionClick: () -> Unit,
    onMyMissionClick: () -> Unit
) {
    var remainingHours by remember { mutableStateOf(calculateRemainingHours()) }
    LaunchedEffect(Unit) {
        while (true) {
            remainingHours = calculateRemainingHours()
            delay(60000L)
        }
    }

    val contentText = mission?.missionTitle ?: "Loading Mission..."

    Column(
        modifier = Modifier.width(figmaDp(343f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(43f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF27292E), RoundedCornerShape(figmaDp(20f)))
                .padding(figmaDp(16f)),
            verticalArrangement = Arrangement.spacedBy(figmaDp(15f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Today's Mission", fontSize = 18.sp, lineHeight = 28.sp, color = Color(0xFFF2F2F2))
                Text("Refreshes in $remainingHours", fontSize = 12.sp, lineHeight = 16.sp, color = Color(0xFFF2F2F2))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(figmaDp(150f))
                    .background(Color(0xFF4B5563), RoundedCornerShape(figmaDp(8f)))
                    .clip(RoundedCornerShape(figmaDp(8f))),
                contentAlignment = Alignment.Center
            ) {
                if (submission != null) {
                    AsyncImage(
                        model = submission.imageUrl,
                        contentDescription = "My Mission",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Mission Challenge Image", fontSize = 14.sp, color = Color(0xFFF2F2F2))
                }
            }

            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(figmaDp(8f))) {
                Text(
                    text = contentText,
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    color = Color(0xFFF2F2F2)
                )
            }

            MissionButtons(onTakeMissionClick, onMyMissionClick)
        }
    }
}


@Composable
fun MissionButtons(onTakeMissionClick: () -> Unit, onMyMissionClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(figmaDp(8f))) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F2), RoundedCornerShape(figmaDp(8f)))
                .clickable { onTakeMissionClick() }
                .padding(figmaDp(10f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.icon_black_camera), contentDescription = null, modifier = Modifier.size(figmaDp(16f)))
                Text("Take Mission Photo", fontSize = 16.sp, color = Color(0xFF121212), modifier = Modifier.padding(start = figmaDp(8f)))
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF121212), RoundedCornerShape(figmaDp(8f)))
                .clickable { onMyMissionClick() }
                .padding(figmaDp(10f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.icon_white_camera), contentDescription = null, modifier = Modifier.size(figmaDp(16f)))
                Text("My Mission Photo", fontSize = 16.sp, color = Color(0xFFF2F2F2), modifier = Modifier.padding(start = figmaDp(8f)))
            }
        }
    }
}

@Composable
fun FeedSection(
    feedList: List<MissionPostDto>,
    currentFilter: FeedFilterType,
    onFilterChange: (FeedFilterType) -> Unit,
    onPostClick: (Long) -> Unit
) {
    var isFilterExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = figmaDp(16f), vertical = figmaDp(16f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(10f))
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Mission Feed", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFF2F2F2))
                FilterButton(
                    currentText = if (currentFilter == FeedFilterType.GLOBAL) "Global" else "Following",
                    onClick = { isFilterExpanded = true }
                )
            }

            if (isFilterExpanded) {
                Popup(
                    alignment = Alignment.TopEnd,
                    onDismissRequest = { isFilterExpanded = false }
                ) {
                    Box(modifier = Modifier.padding(top = figmaDp(35f))) {
                        FeedFilter(
                            currentFilter = currentFilter,
                            onSelect = {
                                onFilterChange(it)
                                isFilterExpanded = false
                            }
                        )
                    }
                }
            }
        }
        FeedGrid(feedList, onPostClick)
    }
}

@Composable
fun FilterButton(currentText: String = "Filter", onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .border(figmaDp(1f), Color(0xFFBDBDBD), RoundedCornerShape(figmaDp(4f)))
            .height(figmaDp(30f))
            .clickable { onClick() }
            .padding(horizontal = figmaDp(13f)),
        horizontalArrangement = Arrangement.spacedBy(figmaDp(4f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = currentText, fontSize = 14.sp, lineHeight = 20.sp, color = Color(0xFFEEEEEE))
        Image(painter = painterResource(id = R.drawable.chevron_down), contentDescription = null, modifier = Modifier.size(10.dp))
    }
}

@Composable
fun FeedFilter(currentFilter: FeedFilterType, onSelect: (FeedFilterType) -> Unit) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(63.dp)
            .background(Color(0xFF121212), RoundedCornerShape(4.dp))
            .padding(vertical = 5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .width(100.dp)
                .height(24.dp)
                .background(if (currentFilter == FeedFilterType.GLOBAL) Color(0x33FFFFFF) else Color.Transparent)
                .clickable { onSelect(FeedFilterType.GLOBAL) }
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Global", fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight(400), color = Color(0xFFEEEEEE), textAlign = TextAlign.Center)
        }
        Row(
            modifier = Modifier
                .width(100.dp)
                .height(24.dp)
                .background(if (currentFilter == FeedFilterType.FOLLOWING) Color(0x33FFFFFF) else Color.Transparent)
                .clickable { onSelect(FeedFilterType.FOLLOWING) }
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Following", fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight(400), color = Color(0xFFEEEEEE), textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun FeedGrid(posts: List<MissionPostDto> = emptyList(), onPostClick: (Long) -> Unit = {}) {
    val rows = posts.chunked(3)
    Column(verticalArrangement = Arrangement.spacedBy(figmaDp(10f))) {
        if (posts.isEmpty()) {
            Box(Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                Text("No posts yet.", color = Color.Gray)
            }
        }
        for (rowItems in rows) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(figmaDp(10f))) {
                for (post in rowItems) {
                    AsyncImage(
                        model = post.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(figmaDp(107.66f)).height(figmaDp(107.66f))
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { onPostClick(post.misPostId) }
                    )
                }
                repeat(3 - rowItems.size) { Spacer(modifier = Modifier.width(figmaDp(107.66f))) }
            }
        }
    }
}

fun calculateRemainingHours(): String {
    val now = Calendar.getInstance()
    val midnight = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 24)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val diffMillis = midnight.timeInMillis - now.timeInMillis
    if (diffMillis < 0) return "0 hours"
    val hours = diffMillis / (1000 * 60 * 60)
    return "$hours hours"
}