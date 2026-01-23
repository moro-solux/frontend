package com.solux.moro.ui.mission.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.Gray20
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.designsystem.theme.MoroTheme.colors
import com.solux.moro.data.dto.response.MissionCommentDto
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(dateString)

        // 출력: 2026.01.24 14:30
        val outputFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        outputFormat.format(date ?: return dateString)
    } catch (e: Exception) {
        dateString
    }
}

@Composable
fun MissionCommentItem(
    item: MissionCommentDto,
    currentNickname: String,
    onEdit: (Long, String) -> Unit,
    onDelete: (Long) -> Unit
) {
    val isMine = item.username == currentNickname
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4C4C4C))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color = Color(0xFFF2F2F2)),
                painter = painterResource(id = R.drawable.img_profile_small),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.username,
                        color = Color.White,
                        style = MoroTheme.typography.bodyRegular16
                    )
                    Text(
                        text = formatDate(item.misCreatedAt),
                        color = colors.gray40,
                        style = MoroTheme.typography.bodyRegular14
                    )
                }
                Text(
                    text = item.misContent,
                    color = Color.White,
                    style = MoroTheme.typography.bodyRegular16
                )
            }
        }

        if (isMine) {
            Box {
                IconButton(onClick = { expanded = true }, modifier = Modifier.size(24.dp)) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "더보기", tint = Color.White)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color(0xFF2C2C2C))
                ) {
                    DropdownMenuItem(
                        text = { Text("수정", style = TextStyle(fontSize = 14.sp, color = Color.White)) },
                        onClick = {
                            expanded = false
                            onEdit(item.misCommentId, item.misContent)
                        },
                        leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp)) }
                    )
                    DropdownMenuItem(
                        text = { Text("삭제", style = TextStyle(fontSize = 14.sp, color = Color(0xFFEA4335))) },
                        onClick = {
                            expanded = false
                            onDelete(item.misCommentId)
                        },
                        leadingIcon = { Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color(0xFFEA4335), modifier = Modifier.size(16.dp)) }
                    )
                }
            }
        }
    }
}

@Composable
fun MissionCommentList(
    comments: List<MissionCommentDto>,
    currentNickname: String,
    onEdit: (Long, String) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
        items(comments) { comment ->
            MissionCommentItem(item = comment, currentNickname = currentNickname, onEdit = onEdit, onDelete = onDelete)
        }
    }
}

@Composable
fun MissionCommentInput(
    initialText: String = "",
    onSendClick: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    LaunchedEffect(initialText) {
        text = initialText
    }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Add a comment…", color = colors.gray40, style = MoroTheme.typography.bodyRegular16) },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotBlank()) {
                        onSendClick(text)
                        text = ""
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_send),
                        contentDescription = "전송",
                        tint = if (text.isNotBlank()) Color.White else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Gray20, unfocusedTextColor = Gray20,
                focusedContainerColor = Color(0xFF171717), unfocusedContainerColor = Color(0xFF171717),
                focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MissionCommentWindow(
    comments: List<MissionCommentDto>,
    currentNickname: String,
    editingComment: Pair<Long, String>?,
    onSend: (String) -> Unit,
    onEditClick: (Long, String) -> Unit,
    onDelete: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (comments.isNotEmpty()) Modifier.height(560.dp) else Modifier.wrapContentHeight())
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color(0xFF4C4C4C))
            .navigationBarsPadding()
            .imePadding()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(41.dp)
                    .height(4.dp)
                    .background(
                        color = Color(0xFFA5A5A5),
                        shape = RoundedCornerShape(50)
                    )
            )
        }

        if (comments.isNotEmpty()) {
            Box(modifier = Modifier.weight(1f)) {
                MissionCommentList(
                    comments = comments,
                    currentNickname = currentNickname,
                    onEdit = onEditClick,
                    onDelete = onDelete
                )
            }
        }

        MissionCommentInput(
            initialText = editingComment?.second ?: "",
            onSendClick = onSend
        )
    }
}