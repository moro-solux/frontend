package com.solux.moro.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.R
import com.solux.moro.components.BottomBar
import com.solux.moro.components.FollowTopBar
import com.solux.moro.components.FollowUserItem
import com.solux.moro.components.toPxDp
import com.solux.moro.components.toPxSp

@Composable
fun FollowScreen(){
    Scaffold(
        bottomBar = { BottomBar() },
        topBar = { FollowTopBar(null,{}) }
    ) {innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color(0xFF121212))
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(43.20000076293945.toPxDp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                        .width(1080.toPxDp)
                        .height(313.92001.toPxDp)
                        .padding(start = 46.08.toPxDp, top = 46.08.toPxDp, end = 46.08.toPxDp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(57.60000228881836.toPxDp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FollowTab("Followers",true,{})
                    FollowTab("Following",false,{})
                }
                TextFiled()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 46.08.toPxDp, end = 46.08.toPxDp)
            ) {
                FollowUserItem()
                FollowUserItem()
            }
        }
    }
}

@Composable
private fun TextFiled(){
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = {
            Text("Search followers...",
                color = Color(0xFFA5A5A5),
                fontSize = 40.32.toPxSp)
        },
        leadingIcon={
            Icon(
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = "image description",
                tint=Color(0xFFBDBDBD)
            )
            },
        modifier = Modifier
            .width(987.84003.toPxDp)
            .border(width = 2.88.toPxDp,
                color = Color(0xFFA5A5A5),
                shape = RoundedCornerShape(size = 23.04.toPxDp)),
        textStyle= TextStyle.Default,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF121212),
            unfocusedContainerColor = Color(0xFF121212)
        )
    )

}

@Composable
private fun FollowTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
){
    var textColor=Color(0xFFFFFFFF)
    var dividerColor= Color(0xFFFFFFFF)

    if(selected){
        textColor=Color(0xFFFFFFFF)
        dividerColor= Color(0xFFFFFFFF)
    }
    else{
        textColor=Color(0xFFA5A5A5)
        dividerColor= Color(0x00FFFFFF)
    }
    Column(
        modifier = Modifier
            .width(206.toPxDp)
            .height(80.64.toPxDp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 46.08.toPxSp,
//           fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = textColor,
                textAlign = TextAlign.Center,
            )
        )
        HorizontalDivider(
            thickness = 5.76.toPxDp,
            color = dividerColor
        )
    }

}

@Preview(device = Devices.PIXEL_4)
@Composable
fun FollowScreenPreview(){
    FollowScreen()
}