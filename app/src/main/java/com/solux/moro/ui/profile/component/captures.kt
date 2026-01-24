package com.solux.moro.ui.profile.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.navigation.FeedRoute
import com.solux.moro.data.model.ProfileFeedItem

@SuppressLint("DiscouragedApi")
@Composable
fun Captures(
    posts: List<ProfileFeedItem>,
    navController: NavHostController,
    isMyProfile:Boolean,
    onAllClick: () -> Unit,
){
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(1091.65552.toPxDp)
            .padding(start = 46.08.toPxDp, end = 46.08.toPxDp),
        verticalArrangement = Arrangement.spacedBy(28.80000114440918.toPxDp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(73.toPxDp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Recent Captures",
                style = TextStyle(
                    fontSize = 51.84.toPxSp,
                    lineHeight = 72.58.toPxSp,
//                   fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFF2F2F2),
                ),
                modifier = Modifier
                    .height(73.toPxDp)
            )
        if(isMyProfile) {
            Text(
                text = "전체보기",
                style = TextStyle(
                    fontSize = 40.toPxSp,
                    lineHeight = 72.58.toPxSp,
//                   fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = MoroTheme.colors.gray40,
                ),
                modifier = Modifier
                    .height(73.toPxDp)
                    .padding(top = 0.dp, end = 0.dp)
                    .clickable {
                        onAllClick()
                    }
            )
        }
        }
        ImageFeedGrid(posts,navController)
    }
}

@Composable
fun ImageFeedGrid(posts: List<ProfileFeedItem>,
                  navController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(989.85.toPxDp),
        verticalArrangement = Arrangement.spacedBy(28.8.toPxDp),
        horizontalArrangement = Arrangement.spacedBy(28.8.toPxDp) 
    ) {
        items(posts) { post ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)) // 살짝 둥글게 처리
                    .background(Color(0xFF2B2B2B)) // 로딩 전 기본 배경색
                    .clickable{
                        Log.d("Clicked", "Clicked $post.id ")
                        navController.navigate(FeedRoute.createRoute(post.id))}
            ) {
                AsyncImage(
                    model = post.imageUrl?: R.drawable.imp_captures_1,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun CapturePreview(){
   // Captures()
}