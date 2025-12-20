package com.solux.moro.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.solux.moro.R

@Composable
fun FollowUserItem() {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_user_profile),
            contentDescription = "image description",
            contentScale = ContentScale.None
        )
    }
}


@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun FollowUserItemPreview(){
    FollowUserItem()
}
