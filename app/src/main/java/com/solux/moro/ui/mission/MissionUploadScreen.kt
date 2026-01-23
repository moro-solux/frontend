package com.solux.moro.ui.mission

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp
import com.solux.moro.ui.mission.component.*

@Composable
fun MissionUploadScreen(
    navController: NavHostController,
    imageUri: Uri,
    missionId: Long,
    viewModel: MissionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val currentMission by viewModel.currentMission.collectAsState()
    val uploadedPost by viewModel.uploadedPost.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val serverAnalysisScore by viewModel.analysisScore.collectAsState()

    val userNickname by viewModel.nickname.collectAsState()

    val missionTitle = currentMission?.missionTitle ?: "Today's Mission"
    val targetColor = currentMission?.targetColor
    val isUploaded = uploadedPost != null

    LaunchedEffect(targetColor) {
        if (!targetColor.isNullOrBlank() && serverAnalysisScore == null) {
            viewModel.analyzeImage(context, imageUri, missionId)
        }
    }

    Scaffold(
        topBar = { TopBarBack(if(isUploaded) "미션 완료" else "미션 업로드", onBackClick = { navController.popBackStack() }) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, Color(0xFFF2F2F2), RoundedCornerShape(24.dp))
                    .width(figmaDp(343f))
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Image(modifier = Modifier.size(14.dp), painter = painterResource(id = R.drawable.mission), contentDescription = null)
                        Text("Capture the color", fontSize = 18.sp, color = Color.White)
                    }

                    if (isUploaded) {
                        MissionCapture(imageUri, missionTitle, uploadedPost?.detail, targetColor, nickname = userNickname)
                    } else {
                        if (!targetColor.isNullOrBlank()) {
                            TargetCapture(imageUri, targetColor, score = serverAnalysisScore, nickname = userNickname)
                        } else {
                            MissionCapture(imageUri, missionTitle, null, targetColor, nickname = userNickname)
                        }
                    }
                    Image(painter = painterResource(id = R.drawable.moro_logo_m), contentDescription = null)
                }
            }


            if (!isUploaded) {
                val isAnalyzing = !targetColor.isNullOrBlank() && serverAnalysisScore == null
                if (isLoading || isAnalyzing) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Upload_Button(
                        onClick = {
                            viewModel.uploadMission(context, imageUri, "Mission Complete!", missionId, serverAnalysisScore ?: 0.0)
                        }
                    )
                }
            }
        }
    }
}