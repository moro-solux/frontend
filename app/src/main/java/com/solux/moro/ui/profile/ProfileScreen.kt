package com.solux.moro.ui.profile

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.solux.moro.R
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.TopBar
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.mapper.ColorMapper
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

    val palette = user.colorPalette.paletteColors
    val nickname = user.nickname ?: "@colorhunter"
    val userColorHex = user.userColorHex ?: "#FFFFFF"
    val colorsCount = stats?.colorsCount?:0
    val followerCount = stats?.followerCount?:1
    val followingCount = stats?.followingCount?:1
    val isFollowing = stats?.isFollowing?:false

    val captures by viewModel.userPosts.collectAsState()

    Scaffold(
        bottomBar = { BottomBar(navController) },
        topBar = { if(isMyProfile){TopBar()}
        else{BackNavigationTopAppBar(nickname,onBackClick = {
            navController.popBackStack()})}}
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
                            userColorHex,
                            colorsCount,
                            followerCount,
                            followingCount,
                            action,
                            navController,
                            isFollowing,
                            onEditProfile = {
                                navController.navigate("profileEdit")
                            },
                            onFollow = {
                                viewModel.onFollow(userId = user.id)
                            },
                            unFollow={
                                viewModel.unFollow(userId = user.id)
                            }
                        )
                        if(!isMyProfile&& user?.visible ==false)
                            NotCaptures()
                        else Captures(captures,
                            navController,
                            isMyProfile,
                            { viewModel.onChangeViewType("USER_COLORS") }
                        ,)
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
                .width(350.dp)
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
                    navController = navController,
                    isMyProfile = isMyProfile,
                    paletteColors =palette,
                    userColor = ColorMapper.toColorFromHex( userColorHex),
                    onAllClick = { viewType ->
                        viewModel.onChangeViewType(viewType)
                    },
                    onColorClick = { colorId ->
                        viewModel.onColorSelected(colorId)
                    }
                )
            }

        }
    }

}
@Composable
fun NotCaptures(){
    Column(
        Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black)
            .padding(start = 16.dp, top = 50.dp, end = 16.dp, bottom = 16.dp)
            ,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            Modifier
                .border(width = 2.dp, color = Color(0xFFF2F2F2), shape = RoundedCornerShape(size = 9999.dp))
                .width(100.dp)
                .height(100.dp)
                .padding(start = 32.dp, top = 36.dp, end = 32.dp, bottom = 36.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.lock),
                contentDescription = "image description",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(1.5.dp)
                    .width(40.dp)
                    .height(40.dp)
            )
        }
        Text(
            text = "비공개 계정입니다",
            style = MoroTheme.typography.bodySemiBold16,
            color= MoroTheme.colors.fontColor
        )

        Text(
            text = "해당 인물의 사진을 보려면 팔로우 승인을 받으세요",
            style = MoroTheme.typography.bodyRegular12,
            color= MoroTheme.colors.fontColor
        )
    }
}


@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun ProfileScreenPreview(){
    NotCaptures()
}
