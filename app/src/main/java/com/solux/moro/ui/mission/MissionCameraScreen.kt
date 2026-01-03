package com.solux.moro.ui.mission


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.solux.moro.ui.camera.CameraLayout

@Composable
fun MissionCameraScreen() {
    var showDialog by remember { mutableStateOf(false) }

    CameraLayout(
        showShotCount = true,
        onCameraClick = { showDialog = true },
        showConfirmDialog = showDialog,
        onConfirm = { showDialog = false },
        onRetry = { showDialog = false },
        cameraContent = { Box(Modifier.fillMaxSize()) }
    )
}


@Preview
@Composable
fun MissionCameraScreenpreview() {
    MissionCameraScreen()
}
