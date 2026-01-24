package com.solux.moro.ui.mission.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.solux.moro.R
import com.solux.moro.core.util.figmaDp

@Composable
fun TargetCapture(
    imageUri: Uri,
    targetHex: String,
    score: Double? = null,
    nickname: String = "User"
) {
    val parsedColor = try { Color(android.graphics.Color.parseColor(targetHex)) } catch (e: Exception) { Color.Gray }

    Column(
        modifier = Modifier.width(figmaDp(193.5f)).height(figmaDp(403f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(16f), Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TargetColorChip(targetHex, parsedColor)

        Box(Modifier.width(figmaDp(151f)).height(figmaDp(205f)).clip(RoundedCornerShape(figmaDp(12f))).background(Color(0xFF525252))) {
            Image(painter = rememberAsyncImagePainter(imageUri), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        }

        Row(Modifier.height(figmaDp(45f)), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (score != null) "${score.toInt()}%" else "Analyzing...",
                style = androidx.compose.ui.text.TextStyle(fontSize = 32.sp, fontWeight = FontWeight(500), color = Color.White)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(figmaDp(28f)), verticalAlignment = Alignment.CenterVertically) {
            ColorCircleItem(title = "Target", color = parsedColor)
            Image(painter = painterResource(id = R.drawable.right), contentDescription = null)
            ColorCircleItem(title = nickname, color = if (score != null) parsedColor.copy(alpha=0.8f) else Color.Gray)
        }
    }
}


@Composable
fun MissionCapture(
    imageUri: Uri,
    missionTitle: String,
    score: String? = null,
    targetColor: String? = null,
    nickname: String = "User"
) {
    Column(modifier = Modifier.width(figmaDp(266f)).padding(figmaDp(14f))) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(Modifier.size(34.dp).clip(CircleShape).background(Color(0xFFD9D9D9)))

            Text(text = nickname, color = Color(0xFFF2F2F2), fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(9.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(missionTitle, color = Color(0xFFA5A5A5), fontSize = 11.sp)

            if (targetColor != null && score != null && score != "-1") {
                val color = try { Color(android.graphics.Color.parseColor(targetColor)) } catch(e:Exception){ Color.Blue }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    TargetColorSmall(color, targetColor)
                    Text("$score%", color = Color(0xFFA5A5A5), fontSize = 11.sp)
                }
            }
        }
        Spacer(Modifier.height(9.dp))

        Box(Modifier.fillMaxWidth().height(figmaDp(317f)).clip(RoundedCornerShape(8.dp)).background(Color(0xFFD9D9D9))) {
            Image(painter = rememberAsyncImagePainter(imageUri), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        }
    }
}


@Composable
fun TargetColorChip(hex: String, color: Color) {
    Row(modifier = Modifier.border(1.dp, Color(0xFFF2F2F2), RoundedCornerShape(50)).background(Color(0xFF121212), RoundedCornerShape(50)).padding(horizontal = 11.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(Modifier.size(16.dp).background(color, CircleShape))
        Text(hex, color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun ColorCircleItem(title: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(Modifier.size(48.dp).border(2.dp, Color(0xFFEEEEEE), CircleShape).background(color, CircleShape))
        Text(title, color = Color(0xFFF2F2F2), fontSize = 12.sp)
    }
}

@Composable
fun TargetColorSmall(color: Color, hex: String) {
    Row(modifier = Modifier.border(0.5.dp, Color.White, RoundedCornerShape(50)).background(Color.Black, RoundedCornerShape(50)).padding(horizontal = 5.dp, vertical = 3.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        Box(Modifier.size(7.dp).background(color, CircleShape))
        Text(hex, color = Color.White, fontSize = 7.sp)
    }
}

@Composable
fun Upload_Button(onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxWidth().height(figmaDp(54f)).background(color = Color(0xFFF2F2F2), shape = RoundedCornerShape(figmaDp(12f))).clickable { onClick() }.padding(figmaDp(16f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.width(figmaDp(120f)), horizontalArrangement = Arrangement.spacedBy(figmaDp(16f), Alignment.Start), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.upload_cloud), contentDescription = "upload", contentScale = ContentScale.None)
            Text(text = "업로드", style = androidx.compose.ui.text.TextStyle(fontSize = 16.sp, fontWeight = FontWeight(400), color = Color(0xFF121212), textAlign = TextAlign.Center))
        }
    }
}