package com.solux.moro.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.TopBar
import com.solux.moro.ui.home.component.CommentWindow
import com.solux.moro.ui.home.component.Feed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedPostId by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        bottomBar = { BottomBar(navController) },
        topBar = { TopBar(true, navController) }
    ) { innerPadding ->
        val feed by viewModel.feed.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)

        ) {
            items(feed) { item ->
                Feed(
                    item = item,
                    onLikeClick = { viewModel.onLikeClick(item.id) },
                    onCommentClick = {
                        selectedPostId = item.id
                        showBottomSheet = true
                    }
                )
            }
        }
    }
    if (showBottomSheet && selectedPostId != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                selectedPostId = null
            },
            sheetState = sheetState,
            containerColor = Color(0xFF4C4C4C),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .width(41.dp)
                        .height(4.dp)
                        .background(color = Color(0xFFA5A5A5), shape = CircleShape)
                )
            }
        ) {
            CommentWindow(postId = selectedPostId!!)
        }
    }
}
