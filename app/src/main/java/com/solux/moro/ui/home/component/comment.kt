package com.solux.moro.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.Gray20
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.designsystem.theme.MoroTheme.colors
import com.solux.moro.data.model.CommentItem
import com.solux.moro.ui.home.CommentViewModel

@Composable
fun Comment(
    item: CommentItem= CommentItem(
        id = "1",
        userNickname = "테스트유저2",
        content = "테스트 댓글",
        createdAt = System.currentTimeMillis()
    )
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4C4C4C))
            .padding(16.dp)
        ,
        Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier
                .padding(0.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(color = Color(0xFFF2F2F2)),
            painter = painterResource(id = R.drawable.img_profile_small),
            contentDescription = "image description",
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier
                .width(310.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.userNickname,
                    color = Color.White,
                    style =MoroTheme.typography.bodyRegular16
                )
                Text(
                    text = item.createdAt.toString(),
                    color = colors.gray40,
                    style =MoroTheme.typography.bodyRegular14

                )
            }
            Text(
                text = item.content,
                color = Color.White,
                style =MoroTheme.typography.bodyRegular16
            )
        }


    }


}

@Composable
fun CommentList(
    comments: List<CommentItem>,
    modifier: Modifier = Modifier,
){
    LazyColumn(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        items(comments.size) { index ->
            Comment(item = comments[index])
        }
    }
}

@Composable
fun CommentWindow(
    postId: String="1",
    viewModel: CommentViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
){
    val commentList by viewModel.comments.collectAsStateWithLifecycle()

    LaunchedEffect(postId) {
        viewModel.setPostId(postId)
    }

    Column(
        modifier=Modifier
            .fillMaxWidth()
            .height(560.dp)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color(0xFF4C4C4C))
            .navigationBarsPadding()
            .imePadding()
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommentList(
            comments = commentList,
            modifier = Modifier.weight(1f)
        )
        CommentInput()
    }
}

@Composable
fun CommentInput(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember { mutableStateOf("") }

        TextField(
            value = text,//viewModel.nicknameInput,
            onValueChange ={ newText ->
                text = newText
            },//viewModel::onNicknameChange,
            placeholder = {
                Text(
                    text = "Add a comment…",
                    color =colors.gray40,
                    style = MoroTheme.typography.bodyRegular16,
                    modifier = Modifier.padding(start=10.dp)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotBlank()) {
                            // TODO: 여기에 저장 로직 추가 (예: viewModel.saveComment(text))
                            println("저장된 댓글: $text")
                            text = ""
                        }
                    },
                    modifier = Modifier
                        .padding(end=10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_send),
                        contentDescription = "댓글 전송",
                        tint = if (text.isNotBlank()) Color.White else Color.Gray ,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Gray20,
                unfocusedTextColor = Gray20,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Gray20,
                focusedContainerColor = Color(0xFF171717),
                unfocusedContainerColor = Color(0xFF171717),
            ),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun CommentInputPreview(){
    CommentInput()
}


@Preview
@Composable
fun CommentPreview(){
    Comment(
        item = CommentItem(
            id = "1",
            userNickname = "@creativedev",
            content = "Mind if I use this as inspiration for my next project?",
            createdAt = System.currentTimeMillis()
        )
    )
}

@Preview
@Composable
fun CommentWindowPreview(){
    CommentWindow()
}