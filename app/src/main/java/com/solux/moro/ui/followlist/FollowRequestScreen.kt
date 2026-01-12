package com.solux.moro.ui.followlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.theme.MoroPalette
import com.solux.moro.core.designsystem.theme.MoroThemeType
import com.solux.moro.data.model.User
import com.solux.moro.data.model.UserColorPalette
import com.solux.moro.data.model.UserStats
import com.solux.moro.ui.followlist.component.FollowRequestUserItem
import com.solux.moro.ui.profile.component.toPxDp

@Composable
    fun FollowRequestScreen(
    viewModel: FollowRequestViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val followRequestUiState by viewModel.uiState.collectAsState()
    val followRequests = followRequestUiState.filteredFollowRequests

    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { BackNavigationTopAppBar("팔로우 요청",onBackClick = {
            navController.popBackStack()
        })}
    ) {innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color(0xFF121212))
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 46.08.toPxDp, end = 46.08.toPxDp)
            ) {
                items(
                    items = followRequests,
                    key = { it.user.id }
                ) { item ->
                    FollowRequestUserItem(
                        user = item.user,
                        stats = item.stats,
                        onAcceptClick = { viewModel.acceptRequest(item.user.id) },
                        onDeclineClick = {viewModel.declineRequest(item.user.id)}
                    )
                }
            }
        }
    }
}


@Preview( device = Devices.PIXEL_4A)
@Composable
fun FollowRequesLazyColumnPreview(){
    val followRequests2 = listOf(
        FollowUserInfo(
            user = User(
                id = 1,
                email = "test@test.com",
                nickname = "테스트유저",
                colorPalette = UserColorPalette(
                    theme = MoroThemeType.Pastel,
                    userColor = MoroPalette.Pastel.Purple400,
                    paletteColors = listOf(
                        MoroPalette.Pastel.Purple400,
                        MoroPalette.Pastel.Yellow300,
                        MoroPalette.Pastel.Green200,
                        MoroPalette.Pastel.Cyan200,
                        MoroPalette.Pastel.Indigo500,
                        MoroPalette.Pastel.Gray400
                    )
                )
            ),
            stats = UserStats(
                colorsCount = 1,
                followerCount = 1,
                followingCount = 1,
                isFollowing = true
            ),
        ),
        FollowUserInfo(
            user = User(
                id = 1,
                email = "test@test.com",
                nickname = "테스트유저",
                colorPalette = UserColorPalette(
                    theme = MoroThemeType.Pastel,
                    userColor = MoroPalette.Pastel.Purple400,
                    paletteColors = listOf(
                        MoroPalette.Pastel.Purple400,
                        MoroPalette.Pastel.Yellow300,
                        MoroPalette.Pastel.Green200,
                        MoroPalette.Pastel.Cyan200,
                        MoroPalette.Pastel.Indigo500,
                        MoroPalette.Pastel.Gray400
                    )
                )
            ),
            stats = UserStats(
                colorsCount = 1,
                followerCount = 1,
                followingCount = 1,
                isFollowing = true
            ),
        ),
        FollowUserInfo(
            user = User(
                id = 1,
                email = "test@test.com",
                nickname = "테스트유저",
                colorPalette = UserColorPalette(
                    theme = MoroThemeType.Pastel,
                    userColor = MoroPalette.Pastel.Purple400,
                    paletteColors = listOf(
                        MoroPalette.Pastel.Purple400,
                        MoroPalette.Pastel.Yellow300,
                        MoroPalette.Pastel.Green200,
                        MoroPalette.Pastel.Cyan200,
                        MoroPalette.Pastel.Indigo500,
                        MoroPalette.Pastel.Gray400
                    )
                )
            ),
            stats = UserStats(
                colorsCount = 1,
                followerCount = 1,
                followingCount = 1,
                isFollowing = true
            ),
        )
    )

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 46.08.toPxDp, end = 46.08.toPxDp)
    ) {
        items(
            items = followRequests2,
        ) { item ->
            FollowRequestUserItem(
                user = item.user,
                stats = item.stats,
                onAcceptClick = { },
                onDeclineClick = {}
            )
        }
    }
}

