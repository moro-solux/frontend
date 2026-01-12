package com.solux.moro.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.solux.moro.R
import com.solux.moro.components.BackNavigationTopAppBar
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.navigation.Profile
import com.solux.moro.data.model.SearchUser
import com.solux.moro.ui.profile.component.toPxDp
import com.solux.moro.ui.profile.component.toPxSp

@Composable
fun SearchUserScreen(
    viewModel: SearchUserViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { BackNavigationTopAppBar("유저 검색",{
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
                TextSearchFiled(
                    query = uiState.searchQuery,
                    onQueryChange = { viewModel.onQueryChanged(it)}
                )
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 46.08.toPxDp, end = 46.08.toPxDp)
            ) {
                items(
                    items = searchResults,
                ) { searchUser ->
                    SearchUserItem(
                        user = searchUser,
                        onItemClick = { id ->
                            // 프로필로 이동
                            navController.navigate(Profile.createRoute(id.toString()))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchUserItem(
    user: SearchUser,
    onItemClick: (Long) -> Unit,
) {
    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(987.84003.toPxDp)
                .height(207.36.toPxDp)
                .padding(top = 34.56.toPxDp, bottom = 34.56.toPxDp)
                .clickable{
                    onItemClick(user.id)
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    23.040000915527344.toPxDp,
                    Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_user_profile),
                    contentDescription = "image description",
                    contentScale = ContentScale.None
                )
                Text(
                    text = user.nickname,
                    style = TextStyle(
                        fontSize = 46.08.toPxSp,
                        lineHeight = 64.51.toPxSp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),
                    ),
                    modifier = Modifier
                        .width(324.toPxDp)
                        .height(65.toPxDp)
                )
            }
        }
    }
    HorizontalDivider(thickness = (2.88 ).toPxDp, color = Color(0xFF262626))
}

@Preview( device = Devices.PIXEL_4A)
@Composable
fun SearchUserItemPreview(){
//    SearchUserItem(
//        user = User(
//            id = 1,
//            email = "test@test.com",
//            nickname = "테스트유저",
//            colorPalette = UserColorPalette(
//                theme = MoroThemeType.Pastel,
//                userColor = MoroPalette.Pastel.Purple400,
//                paletteColors = listOf(
//                    MoroPalette.Pastel.Purple400,
//                    MoroPalette.Pastel.Yellow300,
//                    MoroPalette.Pastel.Green200,
//                    MoroPalette.Pastel.Cyan200,
//                    MoroPalette.Pastel.Indigo500,
//                    MoroPalette.Pastel.Gray400
//                )
//            )
//        ),
//        stats = UserStats(
//            colorsCount = 1,
//            followerCount = 1,
//            followingCount = 1,
//            isFollowing = true
//        ),
//        onActionClick={}
//
//    )
}


@Composable
private fun TextSearchFiled(
    query: String,
    onQueryChange: (String) -> Unit
){
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                // 검색
                println("검색 실행: $text")

                // focusManager.clearFocus()
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

@Preview
@Composable
fun SearchUserScreenPreview(){
    //SearchUserScreen()
}