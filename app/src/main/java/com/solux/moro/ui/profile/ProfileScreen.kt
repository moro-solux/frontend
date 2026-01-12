package com.solux.moro.ui.profile

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.TopBar
import com.solux.moro.ui.profile.component.Captures
import com.solux.moro.ui.profile.component.Palette
import com.solux.moro.ui.profile.component.Profile
import com.solux.moro.ui.profile.component.rememberSidePanelState
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
){
    val action by viewModel.profileAction.collectAsState()
    val isMyProfile by viewModel.isMyProfile.collectAsState()

    val user by viewModel.user.collectAsState()
    val stats by viewModel.stats.collectAsState()

    val nickname by viewModel.nickname.collectAsState()
    val userColor by viewModel.userColor.collectAsState()
    val colorsCount by viewModel.colorsCount.collectAsState()
    val followerCount by viewModel.followerCount.collectAsState()
    val isFollowing by viewModel.isFollowing.collectAsState()
    val followingCount by viewModel.followingCount.collectAsState()

    val posts by viewModel.userPosts.collectAsState()



    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { TopBar() }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        val panelWidthPx = with(LocalDensity.current) { 280.dp.toPx() }
        val offsetX = remember { Animatable(-panelWidthPx) }
        val scope = rememberCoroutineScope()
        val sidePanelState = rememberSidePanelState(panelWidthPx)

        Box(modifier = Modifier
            .padding(innerPadding)) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Column() {
                        Profile(

                            nickname,
                            userColor,
                            followerCount,
                            followingCount,
                            colorsCount,
                            action,
                            navController,
                            onEditProfile = {
                                navController.navigate("profileEdit")
                            },
                            onFollow = {
                        // 팔로우 동작
                            },
                        )
                        Captures(posts)
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .offset(x = (-180).dp, y = (-40).dp)
                    ) {
                        //Palette()
                    }
                }
            }



            Box(modifier = Modifier
                .fillMaxHeight()
                .offset {
                    IntOffset(
                        x = sidePanelState.offsetX.value.toInt(),
                        y = 700
                    )
                }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            sidePanelState.offsetX.snapTo(
                                (sidePanelState.offsetX.value + delta)
                                    .coerceIn(-panelWidthPx, 0f)
                            )
                        }
                    },
                    onDragStopped = {
                        scope.launch {
                            if (sidePanelState.offsetX.value > -panelWidthPx / 2) {
                                sidePanelState.open()
                            } else {
                                sidePanelState.close(panelWidthPx)
                            }
                        }
                    }
                )
                ) {
                Palette(
                    modifier = Modifier,
                    navController = navController
                )
            }

        }
    }

}
@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun ProfileScreenPreview(){
    //ProfileScreen()
}