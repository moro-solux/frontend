package com.solux.moro.ui.notification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.core.designsystem.theme.MoroTheme
import java.time.Instant

@Composable
fun NotificationItem(
    type: NotificationType = NotificationType.COMMENT,
    name:String ?= "_sjwneooo",
    id:String ?= "@uzinnss",
    content:String ?="헐 잘 찍었따",
    createdAt: Instant?= Instant.parse("2025-12-26T10:30:20Z"),

    modifier: Modifier = Modifier,
    color: Color = MoroTheme.colors.fontColor,
    style: TextStyle = MoroTheme.typography.bodySemiBold16
) {
    Row(
        modifier = Modifier
            .border(width = 1.dp,
                color = Color(0xFF404040),
                shape = RoundedCornerShape(size = 8.dp))
            .width(360.dp)
        .background(color = Color(0xFF262626), shape = RoundedCornerShape(size = 8.dp))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = type.iconRes()),
            contentDescription = "image description",
            modifier = Modifier
                .size(40.dp)
        )
        Column(modifier = Modifier
            .padding(horizontal = 10.dp)
            .width(260.dp),
            ) {
            val notification = if (type.needsName && name != null) {
                stringResource(type.messageRes(), name)
            } else {
                stringResource(type.messageRes())
            }

            Text(text = notification,
                color=color,
                style=style,
            )

            if (type.needsContent && id != null && content != null) {
                Row(modifier = Modifier
                    .padding(vertical = 4.dp)
                    .width(250.dp)
                    .height(18.dp)) {
                    Text(
                        text= stringResource(type.contentRes(), id,content),
                        color = Color(0xFFA3A3A3),
                        style = MoroTheme.typography.bodyRegular14,
                    )

                }
            }

            Row() {
                Text(text = "28m ago",
                    color=Color(0xFF737373),
                    style=MoroTheme.typography.bodyRegular12,
                    )
                Spacer(modifier.width(18.dp))
                Text(text = "Reply",
                    color=Color(0xFFA3A3A3),
                    style=MoroTheme.typography.bodyRegular12,
                    )
            }
        }
        Column(modifier = Modifier
            .size(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,) {
            Column(
                modifier = Modifier
                    .size(21.dp)
                    .padding(7.dp)
                    .background(
                        shape = RoundedCornerShape(size = 8.dp),
                        color = Color(0xFF737373),
                    ),
                ) {}
        }
    }
}


@Preview(device = Devices.PIXEL_4)
@Composable
fun NotificationItemPreview(){
    NotificationItem()
}