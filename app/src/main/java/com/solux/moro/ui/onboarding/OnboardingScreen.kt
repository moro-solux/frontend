package com.solux.moro.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.moro.R
import com.solux.moro.core.designsystem.theme.MoroTheme
import kotlinx.coroutines.delay


data class OnboardingPage(
    val title: AnnotatedString,
    @DrawableRes val phoneMockRes: Int? = null,
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit,
) {
    val pages = remember {
        listOf(
            OnboardingPage(
                title = AnnotatedString("moro가 사진 속 대표 색상\n4가지를 자동으로 추출해요"),
                phoneMockRes = R.drawable.img_onboarding1
            ),
            OnboardingPage(
                title = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF60A5FA),
                            fontSize = 20.sp
                        )
                    ) {
                        append("오늘은 청량한 하늘색을 찾아보세요!\n\n")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            fontSize = 23.sp
                        )
                    ) {
                        append(
                            "moro의 미션은 12시간마다\n" +
                            "전 세계 친구들과 함께 할 수 있어요"
                        )
                    }
                },
                phoneMockRes = R.drawable.img_onboarding2
            ),
            OnboardingPage(
                title = AnnotatedString("걸을 때마다 나의 색이\n지도 위에 남아요"),
                phoneMockRes = R.drawable.img_onboarding3
            ),
            OnboardingPage(
                title = AnnotatedString("오른쪽으로 스와이프하면\n나의 팔레트가 펼쳐집니다"),
                phoneMockRes = R.drawable.img_onboarding4
            ),
        )
    }

    val pagerState = rememberPagerState(pageCount = { pages.size })

    var isClosing by remember { mutableStateOf(false) }
    val fadeOutDurationMs = 220
    val screenAlpha by animateFloatAsState(
        targetValue = if (isClosing) 0f else 1f,
        animationSpec = tween(durationMillis = fadeOutDurationMs),
        label = "onboarding_fade_out"
    )

    LaunchedEffect(isClosing) {
        if (isClosing) {
            delay(fadeOutDurationMs.toLong())
            onFinish()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MoroTheme.colors.background)
            .graphicsLayer(alpha = screenAlpha),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            val isLastPage = pagerState.currentPage == pages.lastIndex
            if (isLastPage) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MoroTheme.colors.fontColor,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 4.dp, end = 20.dp)
                        .size(28.dp)
                        .clickable { if (!isClosing) isClosing = true }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp)
            ) {
                DotsIndicator(
                    totalDots = pages.size,
                    selectedIndex = pagerState.currentPage,
                    modifier = Modifier.padding(bottom = 35.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "",
                    modifier = Modifier
                        .width(78.dp)
                        .height(60.dp)
                        .padding(start = 10.dp)
                )
            }
        }

        Spacer(Modifier.height(56.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            OnboardingPageContent(
                page = pages[page],
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = page.title,
            textAlign = TextAlign.Center,
            color = MoroTheme.colors.fontColor,
            style = MoroTheme.typography.bodyRegular23,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        Spacer(Modifier.height(80.dp))

        // 폰 목업 영역
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            if (page.phoneMockRes != null) {
                Image(
                    painter = painterResource(id = page.phoneMockRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.82f)
                        .aspectRatio(0.6f)
                        .clip(
                            RoundedCornerShape(
                                topStart = 28.dp,
                                topEnd = 28.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        ),
                    contentScale = ContentScale.Crop
                )
            } else { }
        }
    }
}

@Composable
private fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(if (isSelected) 13.dp else 12.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color.White else Color.White.copy(alpha = 0.35f)
                    )
            )
        }
    }
}

@Preview(showBackground = true, name = "Onboarding")
@Composable
private fun OnboardingScreenPreview() {
    MoroTheme {
        OnboardingScreen(
            onFinish = {}
        )
    }
}