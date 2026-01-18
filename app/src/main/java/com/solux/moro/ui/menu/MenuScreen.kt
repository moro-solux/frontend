package com.solux.moro.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.solux.moro.core.designsystem.component.BottomBar
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.util.figmaDp

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    navController: NavHostController
) {
    // UI 상태 관리
    val isPublic by viewModel.isPublic.collectAsState()
    val isPushOn by viewModel.isNotificationEnabled.collectAsState()

    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBarBack("메뉴",onBackClick = {
            navController.popBackStack()
        }) },
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .background(Color(0xFF121212))
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(figmaDp(14f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SettingsSection(navController = navController)
                }
                item {
                    SettingOptions(
                        isPublic = isPublic,
                        onPublicToggle = {
                            viewModel.onVisibilityChanged(it)
                        },
                        isPushOn = isPushOn,
                        onPushToggle = {
                            viewModel.onPushSettingsChanged(it)
                        },
                        onLogoutClick = {
                            showLogoutDialog = true
                        }
                    )
                }
            }

            // 로그아웃 다이얼로그 (상태가 true일 때만 표시)
            if (showLogoutDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable { showLogoutDialog = false },
                    contentAlignment = Alignment.Center
                ) {
                    LogoutDialog(
                        onDismiss = { showLogoutDialog = false },
                        onConfirm = {
                            viewModel.performLogout {
                                showLogoutDialog = false

                                // Splash 화면으로 이동
                                navController.navigate("splash") {
                                    popUpTo("menu") { inclusive = true }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = figmaDp(16f),
                top = figmaDp(20f),
                end = figmaDp(16f),
                bottom = figmaDp(16f)
            ),
        verticalArrangement = Arrangement.spacedBy(figmaDp(12f), Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "설정",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(80f))
                .background(
                    color = Color(0xFF171717),
                    shape = RoundedCornerShape(size = figmaDp(16f))
                )
                .border(
                    width = figmaDp(1f),
                    color = Color(0xFFF2F2F2),
                    shape = RoundedCornerShape(size = figmaDp(16f))
                )
                .clip(RoundedCornerShape(size = figmaDp(16f))) // 클릭 리플 효과가 경계 안에서만 돌도록 clip 추가
                .clickable { navController.navigate("colormap") } // 네비게이션 연결 [cite: 2026-01-18]
                .padding(horizontal = figmaDp(20f)),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "colormap",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )

                // 컬러칩 그래디언트 아이콘 영역
                Box(
                    modifier = Modifier
                        .size(figmaDp(40f))
                        .background(
                            color = Color(0xFF464646),
                            shape = RoundedCornerShape(size = figmaDp(8f))
                        )
                        .padding(figmaDp(4f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(figmaDp(4f)))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFF6CB4),
                                        Color(0xFFFFBC4F),
                                        Color(0xFFDEFF9C),
                                        Color(0xFFCAFFC6)
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun SettingOptions(
    isPublic: Boolean,
    onPublicToggle: (Boolean) -> Unit,
    isPushOn: Boolean,
    onPushToggle: (Boolean) -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = figmaDp(16f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        // 공개/비공개 설정
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f))
                .clickable { onPublicToggle(!isPublic) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "공개 / 비공개 설정", fontSize = 16.sp, color = Color(0xFFF2F2F2))
            ToggleButton(isOn = isPublic, onToggle = onPublicToggle)
        }

        // 푸시 알림 설정
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f))
                .clickable { onPushToggle(!isPushOn) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "푸시 알림 설정", fontSize = 16.sp, color = Color(0xFFF2F2F2))
            ToggleButton(isOn = isPushOn, onToggle = onPushToggle)
        }

        Spacer(modifier = Modifier.height(figmaDp(16f)))

        // 로그아웃 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f))
                .clickable { onLogoutClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "로그아웃",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFA5A5A5),
                )
            )
        }
    }
}

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(figmaDp(280f))
            .background(
                color = Color(0xFF121212),
                shape = RoundedCornerShape(size = figmaDp(24.72f))
            )
            .border(1.dp, Color.Gray, RoundedCornerShape(size = figmaDp(24.72f)))
            .padding(figmaDp(24f)),
        verticalArrangement = Arrangement.spacedBy(figmaDp(24f)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "로그아웃 하시겠습니까?",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(figmaDp(12f))
        ) {
            // 취소 버튼
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(figmaDp(44f))
                    .background(Color(0xFFF2F2F2), RoundedCornerShape(figmaDp(12f)))
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Text("취소", color = Color.Black, fontWeight = FontWeight.Bold)
            }
            // 확인 버튼
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(figmaDp(44f))
                    .border(1.dp, Color(0xFFA5A5A5), RoundedCornerShape(figmaDp(12f)))
                    .clickable { onConfirm() },
                contentAlignment = Alignment.Center
            ) {
                Text("확인", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ToggleButton(
    isOn: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .width(figmaDp(48f))
            .height(figmaDp(24f))
            .clip(RoundedCornerShape(figmaDp(999f)))
            .background(color = if (isOn) Color(0xFFF2F2F2) else Color(0xFF737373))
            .clickable { onToggle(!isOn) }
            .padding(figmaDp(2f)),
        horizontalArrangement = if (isOn) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(figmaDp(20f))
                .clip(CircleShape)
                .background(color = if (isOn) Color(0xFFBDBDBD) else Color.White)

        )
    }
}