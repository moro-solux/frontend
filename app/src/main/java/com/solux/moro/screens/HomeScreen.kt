package com.solux.moro.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.components.feed

@Composable
fun HomeScreen(){
    Column(
        Modifier
        .width(375.dp)
        .height(812.dp)
        .background(color = Color(0xFF121212)),
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        ){//홈 화면
        feed()
    }
}
@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}