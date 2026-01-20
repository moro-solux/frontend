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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.util.Patterns
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
    onCloseClick: () -> Unit = {},
    onLocationConsentClick: () -> Unit = {},
    onSignUpClick: (email: String, nickname: String, sensitivity: Int) -> Unit = { _, _, _ -> },
) {
    val isPreview = LocalInspectionMode.current

    var email by remember { mutableStateOf(initialEmail) }
    var nickname by remember { mutableStateOf("") }
    var locationConsented by remember { mutableStateOf(false) }
    var sensitivityInput by remember { mutableStateOf("") }

    val isEmailValid = remember(email) {
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    val sensitivityValue = sensitivityInput.toIntOrNull()
    val isSensitivityValid = sensitivityValue != null && sensitivityValue in 0..100

    LaunchedEffect(initialEmail, isPreview) {
        if (!isPreview && initialEmail.isNotBlank() && email != initialEmail) {
            email = initialEmail
        }
    }

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

        if (!isQuickStart) {
            Text(
                text = "이메일",
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
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        textStyle = MoroTheme.typography.bodyRegular16.copy(
                            color = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (email.isBlank()) {
                                    Text(
                                        text = "이메일을 입력해주세요",
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

            }

            Spacer(modifier = Modifier.height(figmaDp(12f)))

            Text(
                text = when {
                    email.isBlank() -> "이메일을 입력해주세요"
                    isEmailValid -> "올바른 이메일 형식입니다"
                    else -> "이메일 형식이 올바르지 않습니다"
                },
                style = MoroTheme.typography.bodyRegular12.copy(
                    color = when {
                        email.isBlank() -> MoroTheme.colors.gray40
                        isEmailValid -> Color(0xFF9AE6B4)
                        else -> Color(0xFFFF6B6B)
                    }
                )
            )

            Spacer(modifier = Modifier.height(figmaDp(26f)))
        }

        Text(
            text = "닉네임 설정",
            style = MoroTheme.typography.bodyRegular14,
            color = Color.White,
            modifier = Modifier.padding(start = figmaDp(8f))
        )

        Spacer(modifier = Modifier.height(figmaDp(12f)))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(figmaDp(48f))
                .clip(RoundedCornerShape(figmaDp(12f)))
                .background(MoroTheme.colors.gray60)
                .padding(horizontal = figmaDp(16f))
        ) {
            BasicTextField(
                value = nickname,
                onValueChange = { nickname = it },
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

        Spacer(modifier = Modifier.height(figmaDp(12f)))

        if (!isQuickStart) {
            Text(
                text = "영문으로만 작성해주세요",
                style = MoroTheme.typography.bodyRegular12.copy(
                    color = MoroTheme.colors.gray40
                )
            )

            Spacer(modifier = Modifier.height(figmaDp(26f)))

            Text(
                text = "민감도 설정 (0~100)",
                style = MoroTheme.typography.bodyRegular14,
                color = Color.White,
                modifier = Modifier.padding(start = figmaDp(8f))
            )

            Spacer(modifier = Modifier.height(figmaDp(12f)))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(figmaDp(48f))
                    .clip(RoundedCornerShape(figmaDp(12f)))
                    .background(MoroTheme.colors.gray60)
                    .padding(horizontal = figmaDp(16f))
            ) {
                BasicTextField(
                    value = sensitivityInput,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            sensitivityInput = input
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = MoroTheme.typography.bodyRegular16.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (sensitivityInput.isBlank()) {
                                Text(
                                    text = "0~100 사이 정수",
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

            Spacer(modifier = Modifier.height(figmaDp(12f)))

            Text(
                text = "민감도는 0~100 사이 정수만 가능합니다",
                style = MoroTheme.typography.bodyRegular12.copy(
                    color = if (isSensitivityValid || sensitivityInput.isBlank()) {
                        MoroTheme.colors.gray40
                    } else {
                        Color(0xFFFF6B6B)
                    }
                )
            )

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
        }

        val isSignUpEnabled = if (isQuickStart) {
            nickname.isNotBlank()
        } else {
            isEmailValid &&
                nickname.isNotBlank() &&
                locationConsented &&
                isSensitivityValid
        }
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
                            val effectiveEmail = if (isQuickStart) "" else email
                            val effectiveSensitivity = if (isQuickStart) {
                                0
                            } else {
                                sensitivityValue ?: return@noRippleClickable
                            }
                            onSignUpClick(effectiveEmail, nickname, effectiveSensitivity)
                        }
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isQuickStart) "시작하기" else "가입하기",
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
