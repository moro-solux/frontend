package com.solux.moro.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.model.FeedItem
import com.solux.moro.ui.profile.component.toPxDp
import com.solux.moro.ui.profile.component.toPxSp


@Composable
fun Feed(
    modifier: Modifier=Modifier,
    item: FeedItem,
    isMyPost: Boolean = false,
    onProfileClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit={},
    onEditClick : () -> Unit={},
    onDeleteClick : () -> Unit={},
    ) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy((30.461544036865234).toPxDp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height((1442.17859).toPxDp)
            .padding(
                start = (46.08).toPxDp,
                top = (46.08).toPxDp,
                end = (46.08).toPxDp,
                bottom = (46.08).toPxDp
            )
    ) {
        Row(
            Modifier
                .width((987.84003).toPxDp)
                .height((110.76924).toPxDp)
                .clickable{
                    onProfileClick()
                },
            horizontalArrangement = Arrangement.spacedBy(
                (28.80000114440918).toPxDp,
                Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(0.dp)
                    .width((110.76924).toPxDp)
                    .height((110.76924).toPxDp)
                    .clip(CircleShape)
                    .background(color = Color(0xFFF2F2F2)),
                painter = painterResource(id = R.drawable.img_profile_small),
                contentDescription = "image description",
                contentScale = ContentScale.FillHeight
            )
            Row(
                Modifier
                    .width((847.38458).toPxDp)
                    .height((110.76924).toPxDp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.Start
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height((65).toPxDp)
                ) {
                    Text(
                        text = item.authorNickname, //"@colorlover",
                        style = TextStyle(
                            fontSize = (46.08).toPxSp,
                            lineHeight = (64.51).toPxSp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFF2F2F2),
                        ),
                        modifier = Modifier
                            .height((65).toPxDp)
                    )
                    Text(
                        text = item.createdAt, //"2h ago",
                        style = TextStyle(
                            fontSize = (34.56).toPxSp,
                            lineHeight = (48.38).toPxSp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFA5A5A5),
                        ),
                    )
                }
                if(isMyPost) {
                    FeedMoreMenu(
                        onEditClick = { /*TODO*/ },
                        onDeleteClick = { onDeleteClick() }
                    )
                }
            }
        }

        Row(
            Modifier
                .width((987.84003).toPxDp)
                .height((1044.4801).toPxDp)
                .padding(top = (28.8).toPxDp, bottom = (28.8).toPxDp),
            horizontalArrangement = Arrangement.spacedBy(
                (23.040000915527344).toPxDp,
                Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .width((740.16003).toPxDp)
                    .height((986.88007).toPxDp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl ?: R.drawable.img_feed)
                    .build(),
                placeholder = painterResource(R.drawable.img_feed),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )

            LazyColumn(
                Modifier// 색상 칩
                    .width((224.64001).toPxDp)
                    .height((986.88007).toPxDp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                items(item.contentColors) { color ->
                    Box(
                        Modifier
                            .width((224.64001).toPxDp)
                            .height((224.64001).toPxDp)
                            .background(
                                color = color,
                                shape = RoundedCornerShape(size = (3.84615).toPxDp)
                            )
                    )
                }
            }
        }
        LazyRow(
            Modifier// 색상 코드
                .width((987.84003).toPxDp)
                .height((48).toPxDp),
            horizontalArrangement = Arrangement.spacedBy(22.15384864807129.toPxDp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(item.hexCodes) { hexCode ->
                Text(
                    text = hexCode,
                    style = TextStyle(
                        fontSize = (34.56).toPxSp,
                        lineHeight = (48.38).toPxSp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA5A5A5),
                    ),
                    modifier = Modifier
                        .width((143).toPxDp)
                        .height((48).toPxDp)
                )
            }
        }
        Row(
            Modifier
                .width((987.84003).toPxDp)
                .height((55.38462).toPxDp),
            horizontalArrangement = Arrangement.spacedBy(
                (28.80000114440918).toPxDp,
                Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 댓글
            Row(
                horizontalArrangement = Arrangement.spacedBy((11.52).toPxDp, Alignment.Start)
            ) {
                IconButton(
                    onClick = {
                        onCommentClick()
                    },
                    modifier = Modifier.size((55.38462).toPxDp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_chat),
                        contentDescription = "More options",
                        tint = Color.White,
                        modifier = Modifier
                            .padding((0.09736).toPxDp)
                            .size((49.85878).toPxDp)

                    )
                }
                Text(
                    text = item.commentCount.toString(),
                    style = TextStyle(
                        fontSize = (40.32).toPxSp,
                        lineHeight = (56.45).toPxSp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width((24.92308).toPxDp)
                        .height((55.38462).toPxDp)
                )
            }

            Row(   // 좋아요??????
                horizontalArrangement = Arrangement.spacedBy((11.52).toPxDp, Alignment.Start)
            ) {
                IconButton(
                    onClick = {
                        onLikeClick()
                    },
                    modifier = Modifier.size((55.38462).toPxDp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.like),
                        contentDescription = "More options",
                        tint = if (item.isLiked) Color(0xFFFF5252) else Color.White,
                        modifier = Modifier
                            .padding((0.09736).toPxDp)
                            .size((49.85878).toPxDp)
                    )
                }
                Text(
                    text = item.likeCount.toString(),
                    style = TextStyle(
                        fontSize = (40.32).toPxSp,
                        lineHeight = (56.45).toPxSp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width((24.92308).toPxDp)
                        .height((55.38462).toPxDp)
                )
            }

        }
    }
}

@Composable
fun FeedMoreMenu(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expanded = true }, // 클릭 시 메뉴 오픈
            modifier = Modifier.size((69.12).toPxDp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.White,
                modifier = Modifier
                    .padding((2.88).toPxDp)
                    .size((69.12).toPxDp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(142.dp)
                .background(Color(0xFF2B2B2B))
        ) {
            DropdownMenuItem(
                leadingIcon ={
                    Icon(
                        painter = painterResource(id = R.drawable.icon_trash),
                        contentDescription = "삭제 아이콘",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                },
                text = {
                    Text(
                        "삭제",
                        color = Color.Red,
                    style = MoroTheme.typography.bodyRegular16,
                    )
                },
                onClick = {
                    expanded = false
                    onDeleteClick()
                }
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun FeedPreview(){

}