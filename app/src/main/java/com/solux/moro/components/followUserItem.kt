package com.solux.moro.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.R

@Composable
fun FollowUserItem() {
    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(987.84003.toPxDp)
                .height(207.36.toPxDp)
                .padding(top = 34.56.toPxDp, bottom = 34.56.toPxDp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    23.040000915527344.toPxDp,
                    Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_user_profile),
                    contentDescription = "image description",
                    contentScale = ContentScale.None
                )
                Text(
                    text = "@sarahjohnson",
                    style = TextStyle(
                        fontSize = 46.08.toPxSp,
                        lineHeight = 64.51.toPxSp,
                        //               fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF2F2F2),
                    ),
                    modifier = Modifier
                        .width(324.toPxDp)
                        .height(65.toPxDp)
                )
            }
            followButton(true,{})
        }
    }
    HorizontalDivider(thickness = (2.88 ).toPxDp, color = Color(0xFF262626))
}


@Composable
private fun followButton(
    isFollow: Boolean,
    onClick: () -> Unit
){
    var containerColor = Color(0xFF262626)
    var contentColor = Color(0xFFF2F2F2)
    var borderColor=Color(0xFF404040)
    var text="Following"

    if(isFollow){
        containerColor = Color(0xFF262626)
        contentColor = Color(0xFFF2F2F2)
        borderColor=Color(0xFF404040)
        text="Following"
    }
    else{
        containerColor =  Color(0xFFF2F2F2)
        contentColor = Color(0xFF121212)
        borderColor= Color(0xFF262626)
        text="Follow"
    }
    Button(
        onClick = {},
        modifier = Modifier
            .width(259.20001.toPxDp)
            .height(97.92.toPxDp), colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ), shape = RoundedCornerShape(size = 23.04.toPxDp),
        contentPadding = PaddingValues(
            horizontal = 0.dp,
            vertical = 0.dp
        )
        , border = BorderStroke(width = 2.88.toPxDp, color =borderColor)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 40.32.toPxSp,
//                        fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),

                textAlign = TextAlign.Center,
            )
        )
    }
}

@Preview(
    device = Devices.PIXEL_4A)
@Composable
fun FollowUserItemPreview(){
    FollowUserItem()
}
