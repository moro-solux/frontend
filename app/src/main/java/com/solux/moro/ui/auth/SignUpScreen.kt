package com.solux.moro.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solux.moro.R
import com.solux.moro.core.designsystem.component.top.TopBarBack
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.util.figmaDp
import com.solux.moro.core.util.noRippleClickable

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    initialEmail: String = "",
    isQuickStart: Boolean = false,
    errorMessage: String? = null,
    isRegistering: Boolean = false,
    nicknameCheckState: NicknameCheckState = NicknameCheckState.Idle,
    onCloseClick: () -> Unit = {},
    onLocationConsentClick: () -> Unit = {},
    onNicknameChange: (String) -> Unit = {},
    onNicknameCheckClick: (String) -> Unit = {},
    onSignUpClick: (email: String, nickname: String, sensitivity: Int) -> Unit = { _, _, _ -> },
) {
    var nickname by remember { mutableStateOf("") }
    var locationConsented by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MoroTheme.colors.background)
            .padding(horizontal = figmaDp(16f))
    ) {
        TopBarBack(
            title = "회원가입",
            navigationIconResId = R.drawable.ic_close,
            showBottomDivider = false,
            onBackClick = onCloseClick
        )

        Spacer(modifier = Modifier.height(figmaDp(16f)))

        Text(
            text = "닉네임 설정",
            style = MoroTheme.typography.bodyRegular14,
            color = Color.White,
            modifier = Modifier.padding(start = figmaDp(8f))
        )

        Spacer(modifier = Modifier.height(figmaDp(12f)))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(figmaDp(48f))
                    .clip(RoundedCornerShape(figmaDp(12f)))
                    .background(MoroTheme.colors.gray60)
                    .padding(horizontal = figmaDp(16f))
            ) {
                BasicTextField(
                    value = nickname,
                    onValueChange = { value ->
                        nickname = value
                        onNicknameChange(value)
                    },
                    singleLine = true,
                    textStyle = MoroTheme.typography.bodyRegular16.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (nickname.isBlank()) {
                                Text(
                                    text = "닉네임을 설정해주세요",
                                    style = MoroTheme.typography.bodyRegular16.copy(
                                        color = MoroTheme.colors.gray40
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.width(figmaDp(8f)))

            val isChecking = nicknameCheckState is NicknameCheckState.Checking
            val canCheck = nickname.isNotBlank() && !isChecking
            val checkButtonColor = if (canCheck) MoroTheme.colors.fontColor else MoroTheme.colors.gray50
            val checkTextColor = if (canCheck) MoroTheme.colors.background else MoroTheme.colors.fontColor

            Box(
                modifier = Modifier
                    .height(figmaDp(48f))
                    .clip(RoundedCornerShape(figmaDp(12f)))
                    .background(checkButtonColor)
                    .then(
                        if (canCheck) {
                            Modifier.noRippleClickable { onNicknameCheckClick(nickname) }
                        } else {
                            Modifier
                        }
                    )
                    .padding(horizontal = figmaDp(14f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isChecking) "확인중" else "중복확인",
                    style = MoroTheme.typography.bodyRegular14.copy(
                        color = checkTextColor
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(figmaDp(12f)))

        Text(
            text = "영문으로만 작성해주세요",
            style = MoroTheme.typography.bodyRegular12.copy(
                color = MoroTheme.colors.gray40
            )
        )

        if (nicknameCheckState is NicknameCheckState.Unavailable) {
            Spacer(modifier = Modifier.height(figmaDp(8f)))
            Text(
                text = "닉네임이 중복입니다",
                style = MoroTheme.typography.bodyRegular12.copy(
                    color = Color(0xFFFF6B6B)
                )
            )
        } else if (nicknameCheckState is NicknameCheckState.Available) {
            Spacer(modifier = Modifier.height(figmaDp(8f)))
            Text(
                text = "사용 가능한 닉네임입니다",
                style = MoroTheme.typography.bodyRegular12.copy(
                    color = Color(0xFF4CAF50)
                )
            )
        }

        Spacer(modifier = Modifier.height(figmaDp(26f)))

        val consentColor = if (locationConsented) {
            MoroTheme.colors.fontColor
        } else {
            MoroTheme.colors.gray40
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f))
                .clip(RoundedCornerShape(figmaDp(12f)))
                .background(MoroTheme.colors.background)
                .border(
                    width = 1.dp,
                    color = consentColor,
                    shape = RoundedCornerShape(figmaDp(12f))
                )
                .padding(horizontal = figmaDp(17f))
                .noRippleClickable {
                    locationConsented = !locationConsented
                    onLocationConsentClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "",
                modifier = Modifier
                    .width(figmaDp(18f))
                    .height(figmaDp(18f)),
                colorFilter = ColorFilter.tint(consentColor)
            )

            Spacer(modifier = Modifier.width(figmaDp(15f)))

            Text(
                text = "위치 측정 동의",
                style = MoroTheme.typography.bodyRegular14.copy(
                    color = consentColor
                )
            )
        }

        Spacer(modifier = Modifier.height(figmaDp(26f)))

        val isNicknameAvailable = nicknameCheckState is NicknameCheckState.Available
        val isSignUpEnabled =
            nickname.isNotBlank() && isNicknameAvailable && locationConsented && !isRegistering
        val signUpContainerColor = if (isSignUpEnabled) MoroTheme.colors.fontColor else MoroTheme.colors.gray50
        val signUpTextColor = if (isSignUpEnabled) MoroTheme.colors.background else MoroTheme.colors.fontColor

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f))
                .clip(RoundedCornerShape(figmaDp(12f)))
                .background(signUpContainerColor)
                .then(
                    if (isSignUpEnabled) {
                        Modifier.noRippleClickable {
                            onSignUpClick(initialEmail, nickname, 0)
                        }
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isRegistering) "가입중" else "가입하기",
                style = MoroTheme.typography.bodyRegular16.copy(
                    color = signUpTextColor
                )
            )
        }

        if (!errorMessage.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(figmaDp(12f)))
            Text(
                text = errorMessage,
                style = MoroTheme.typography.bodyRegular12.copy(
                    color = Color(0xFFFF6B6B)
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(
    name = "SignUp - Medium",
    device = "spec:width=360dp,height=800dp,dpi=440",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
private fun SignUpScreenPreview() {
    MoroTheme {
        SignUpScreen()
    }
}
