package com.solux.moro.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.components.BottomBar
import com.solux.moro.components.Captures
import com.solux.moro.components.Profile
import com.solux.moro.components.TopBar

@Composable
fun ProfileScreen(){
    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { TopBar() }
    ) {innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF121212))
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Box() {
                Column() {
                    Profile()
                    Captures()
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = (-180).dp, y=(-40).dp)
                ){
                    //Palette()
                }
            }
        }
    }
}
@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}