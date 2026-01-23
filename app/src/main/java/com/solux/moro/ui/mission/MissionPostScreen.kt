package com.solux.moro.ui.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.solux.moro.data.dto.response.MissionPostDto
import com.solux.moro.ui.mission.component.MissionCommentWindow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionPostScreen(
    navController: NavHostController,
    misPostId: Long,
    viewModel: MissionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val feedList by viewModel.missionFeed.collectAsState()
    val myMissions by viewModel.myMissions.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val nickname by viewModel.nickname.collectAsState()


    val post = remember(feedList, myMissions, misPostId) {
        feedList.find { it.misPostId == misPostId }
            ?: myMissions.find { it.misPostId == misPostId }
    }

    var showCommentSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    var editingComment by remember { mutableStateOf<Pair<Long, String>?>(null) }


    LaunchedEffect(misPostId) {
        viewModel.loadComments(misPostId)
    }

    Scaffold(
        topBar = { TopBarBack("미션 게시물", onBackClick = { navController.popBackStack() }) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        if (post != null) {
            LazyColumn(
                modifier = Modifier
                    .background(Color(0xFF121212))
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(figmaDp(14f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    MissionPost(
                        post = post,
                        commentCount = comments.size,
                        onShareClick = { viewModel.getShareUrl(context, misPostId) },
                        onDeleteClick = {
                            viewModel.deleteMissionPost(misPostId) {
                                navController.popBackStack()
                            }
                        },
                        onCommentClick = {
                            showCommentSheet = true
                            editingComment = null
                        }
                    )
                }
            }
        }
    }

    if (showCommentSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showCommentSheet = false
                editingComment = null
            },
            sheetState = sheetState,
            containerColor = Color.Transparent,
            dragHandle = null
        ) {
            MissionCommentWindow(
                comments = comments,
                currentNickname = nickname,
                editingComment = editingComment,


                onSend = { content ->
                    val currentEdit = editingComment
                    if (currentEdit != null) {

                        viewModel.editComment(misPostId, currentEdit.first, content)
                        editingComment = null
                    } else {
                        viewModel.createComment(misPostId, content)
                    }
                },

                onEditClick = { commentId, currentContent ->
                    editingComment = commentId to currentContent
                },

                onDelete = { commentId -> viewModel.deleteComment(misPostId, commentId) }
            )
        }
    }
}

@Composable
fun MissionPost(
    post: MissionPostDto,
    commentCount: Int,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }


    val hasScore = post.detail != null && post.detail != "-1"

    Column(
        modifier = Modifier
            .width(figmaDp(375f))
            .padding(figmaDp(16f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(10.57f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().height(figmaDp(38.46f)),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(10f)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.profileframe),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(figmaDp(38f))
            )

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "@${post.userName}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600), color = Color(0xFFF2F2F2))
                )

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color(0xFF2C2C2C))
                    ) {
                        DropdownMenuItem(
                            text = { Text("공유하기", color = Color.White) },
                            onClick = {
                                expanded = false
                                onShareClick()
                            },
                            leadingIcon = {
                                Icon(Icons.Outlined.Share, contentDescription = null, tint = Color.White)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("삭제하기", color = Color(0xFFFF5252)) },
                            onClick = {
                                expanded = false
                                onDeleteClick()
                            },
                            leadingIcon = {
                                Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color(0xFFFF5252))
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = post.missionTitle,
                style = TextStyle(fontSize = 12.sp, color = Color(0xFFA5A5A5))
            )


            if (hasScore) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    /* val targetHex = "#FFFFFF"
                    TargetColorSmall(Color.White, targetHex)
                    */

                    Text(
                        text = "${post.detail}%",
                        style = TextStyle(fontSize = 12.sp, color = Color(0xFFA5A5A5))
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(457f))
                .clip(RoundedCornerShape(figmaDp(10f)))
                .background(Color(0xFF1E1E1E))
        ) {
            AsyncImage(
                model = post.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(figmaDp(4f)),
            modifier = Modifier.clickable { onCommentClick() }
        ) {
            Image(painter = painterResource(id = R.drawable.icon_chat), contentDescription = null)
            Text(
                text = "$commentCount",
                style = TextStyle(fontSize = 14.sp, color = Color(0xFFF2F2F2))
            )
        }
    }
}