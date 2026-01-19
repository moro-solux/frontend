package com.solux.moro.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.solux.moro.R
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.ui.followlist.FollowingViewModel
import com.solux.moro.ui.followlist.component.FollowUserItem
import com.solux.moro.ui.profile.component.toPxDp
import com.solux.moro.ui.profile.component.toPxSp

enum class FollowTabType {
    FOLLOWER, FOLLOWING
}
@Composable
fun FollowScreen(
    viewModel: FollowingViewModel = hiltViewModel(),
    navController: NavHostController
){
    val uiState by viewModel.uiState.collectAsState()

    val selectedTab = uiState.selectedTab
    val followers = uiState.filteredFollowers
    val followings = uiState.filteredFollowings
    val userId = ""

    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { BackNavigationTopAppBar(userId,{
            navController.popBackStack()
        }) }
    ) {innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color(0xFF121212))
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(43.20000076293945.toPxDp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                        .width(1080.toPxDp)
                        .height(313.92001.toPxDp)
                        .padding(start = 46.08.toPxDp, top = 46.08.toPxDp, end = 46.08.toPxDp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(57.60000228881836.toPxDp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FollowTab(
                        text = "Followers",
                        selected = selectedTab == FollowTabType.FOLLOWER,
                        onClick = { viewModel.switchTab(FollowTabType.FOLLOWER) }
                    )
                    FollowTab(
                        text = "Following",
                        selected = selectedTab == FollowTabType.FOLLOWING,
                        onClick = { viewModel.switchTab(FollowTabType.FOLLOWING) }
                    )
                }
                TextSearchField(
                    query = if (selectedTab == FollowTabType.FOLLOWER) uiState.followerSearchQuery else uiState.followingSearchQuery,
                    onQueryChange = {
                        if (selectedTab == FollowTabType.FOLLOWER) viewModel.updateFollowerSearch(it)
                        else viewModel.updateFollowingSearch(it)
                    }
                )
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 46.08.toPxDp, end = 46.08.toPxDp)
            ) {
                val currentList = if (selectedTab == FollowTabType.FOLLOWING) followings else followers
                items(
                    items = currentList,
                    key = { it.userId }
                ) { item ->
                    FollowUserItem(
                        user = item,
                        isFollowTab = selectedTab == FollowTabType.FOLLOWING,
                        onActionClick = {
                            if (selectedTab == FollowTabType.FOLLOWING) {
                                viewModel.unFollow(item.userId)
                            } else {
                                viewModel.removeFollower(item.userId)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TextSearchField(
    query: String,
    onQueryChange: (String) -> Unit
){
    val focusManager = LocalFocusManager.current
    TextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                println("검색 실행: $query")
            }
        ),
        placeholder = {
            Text("Search followers...",
                color = Color(0xFFA5A5A5),
                fontSize = 40.32.toPxSp)
        },
        leadingIcon={
            Icon(
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = "image description",
                tint=Color(0xFFBDBDBD)
            )
        },
        modifier = Modifier
            .width(987.84003.toPxDp)
            .border(width = 2.88.toPxDp,
                color = Color(0xFFA5A5A5),
                shape = RoundedCornerShape(size = 23.04.toPxDp)),
        textStyle= TextStyle.Default,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF121212),
            unfocusedContainerColor = Color(0xFF121212),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
        )
    )
}

@Composable
private fun FollowTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
){
    var textColor=Color(0xFFFFFFFF)
    var dividerColor= Color(0xFFFFFFFF)

    if(selected){
        textColor=Color(0xFFFFFFFF)
        dividerColor= Color(0xFFFFFFFF)
    }
    else{
        textColor=Color(0xFFA5A5A5)
        dividerColor= Color(0x00FFFFFF)
    }
    Column(
        modifier = Modifier
            .width(206.toPxDp)
            .height(80.64.toPxDp)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 46.08.toPxSp,
                fontWeight = FontWeight(400),
                color = textColor,
                textAlign = TextAlign.Center,
            )
        )
        HorizontalDivider(
            thickness = 5.76.toPxDp,
            color = dividerColor
        )
    }

}


@Preview
@Composable
fun FollowTabPreview(){
    FollowTab(
        text = "Followers",
        selected = true,
        onClick = {}
    )
}
//@Preview(device = Devices.PIXEL_4)
//@Composable
//fun FollowScreenPreview(){
//    Column {
//        FollowUserItem(
//            user = User(
//                id = 1,
//                email = "test@test.com",
//                nickname = "테스트유저",
//                colorPalette = UserColorPalette(
//                    theme = MoroThemeType.Pastel,
//                    userColor = MoroPalette.Pastel.Purple400,
//                    paletteColors = listOf(
//                        MoroPalette.Pastel.Purple400,
//                        MoroPalette.Pastel.Yellow300,
//                        MoroPalette.Pastel.Green200,
//                        MoroPalette.Pastel.Cyan200,
//                        MoroPalette.Pastel.Indigo500,
//                        MoroPalette.Pastel.Gray400
//                    )
//                )
//            ),
//            stats = UserStats(
//                colorsCount = 1,
//                followerCount = 1,
//                followingCount = 1,
//                isFollowing = true
//            ),
//            isFollowTab = true,
//            onActionClick = {}
//        )
//        FollowUserItem(
//            user = User(
//                id = 2,
//                email = "test2@test.com",
//                nickname = "테스트유저2",
//                colorPalette = UserColorPalette(
//                    theme = MoroThemeType.Pastel,
//                    userColor = MoroPalette.Pastel.Purple400,
//                    paletteColors = listOf(
//                        MoroPalette.Pastel.Purple400,
//                        MoroPalette.Pastel.Yellow300,
//                        MoroPalette.Pastel.Green200,
//                        MoroPalette.Pastel.Cyan200,
//                        MoroPalette.Pastel.Indigo500,
//                        MoroPalette.Pastel.Gray400
//                    )
//                )
//            ),
//            stats = UserStats(
//                colorsCount = 1,
//                followerCount = 1,
//                followingCount = 1,
//                isFollowing = true
//            ),
//            isFollowTab = false,
//            onActionClick = {}
//        )
//        FollowUserItem(
//            user = User(
//                id = 2,
//                email = "test2@test.com",
//                nickname = "테스트유저2",
//                colorPalette = UserColorPalette(
//                    theme = MoroThemeType.Pastel,
//                    userColor = MoroPalette.Pastel.Purple400,
//                    paletteColors = listOf(
//                        MoroPalette.Pastel.Purple400,
//                        MoroPalette.Pastel.Yellow300,
//                        MoroPalette.Pastel.Green200,
//                        MoroPalette.Pastel.Cyan200,
//                        MoroPalette.Pastel.Indigo500,
//                        MoroPalette.Pastel.Gray400
//                    )
//                )
//            ),
//            stats = UserStats(
//                colorsCount = 1,
//                followerCount = 1,
//                followingCount = 1,
//                isFollowing = false
//            ),
//            isFollowTab = false,
//            onActionClick = {}
//        )
//    }
//}