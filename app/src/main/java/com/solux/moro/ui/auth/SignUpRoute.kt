package com.solux.moro.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpRoute(
    initialEmail: String,
    onClose: () -> Unit,
    onSuccess: () -> Unit,
    quickStart: Boolean = false,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    SignUpScreen(
        initialEmail = initialEmail,
        errorMessage = if (quickStart) null else viewModel.errorMessage,
        isRegistering = viewModel.isRegistering,
        nicknameCheckState = viewModel.nicknameCheckState,
        onCloseClick = onClose,
        isQuickStart = quickStart,
        onNicknameChange = { viewModel.resetNicknameCheck() },
        onNicknameCheckClick = { nickname ->
            coroutineScope.launch { viewModel.checkNickname(nickname) }
        },
        onSignUpClick = { email, nickname, sensitivity ->
            if (quickStart) {
                onSuccess()
            } else {
                coroutineScope.launch {
                    val success = viewModel.completeRegistration(email, nickname, sensitivity)
                    if (success) {
                        onSuccess()
                    }
                }
            }
        }
    )
}
