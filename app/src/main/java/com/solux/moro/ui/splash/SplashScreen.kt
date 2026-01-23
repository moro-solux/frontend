package com.solux.moro.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.util.figmaDp
import com.solux.moro.core.util.noRippleClickable
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onGoogleStartClick: () -> Unit = {},
    onSkipClick: () -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current

    var showIntro by remember { mutableStateOf(isPreview) }

    LaunchedEffect(Unit) {
        if (!isPreview) {
            delay(3_000)
            showIntro = true
        }
    }

    val logoOffsetY by animateDpAsState(
        targetValue = if (showIntro) figmaDp(-25f) else 0.dp,
        label = "logoOffset"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MoroTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Moro logo",
                modifier = Modifier
                    .offset(y = logoOffsetY)
                    .size(124.dp)
                    .padding(start = figmaDp(10f))
            )

            AnimatedVisibility(
                visible = showIntro,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = "오늘, Moro와 함께\n하루를 물들여보세요",
                        style = MoroTheme.typography.bodyRegular23,
                        color = MoroTheme.colors.fontColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(figmaDp(82f)))

                    Image(
                        painter = painterResource(id = R.drawable.img_google_login),
                        contentDescription = "Google Login",
                        modifier = Modifier
                            .width(figmaDp(272f))
                            .height(figmaDp(53f))
                            .noRippleClickable { onGoogleStartClick() }
                    )

                    Spacer(modifier = Modifier.height(figmaDp(14f)))

                    Text(
                        text = "간단하게 시작하기",
                        style = MoroTheme.typography.bodyRegular14,
                        color = MoroTheme.colors.gray40,
                        modifier = Modifier.noRippleClickable { onSkipClick() }
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Splash - Medium",
    device = "spec:width=360dp,height=800dp,dpi=440",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
private fun SplashScreenPreview() {
    MoroTheme {
        SplashScreen()
    }
}
