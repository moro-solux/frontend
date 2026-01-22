package com.solux.moro.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.solux.moro.BuildConfig
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.data.network.NetworkModule
import com.solux.moro.data.repository.menurepo.SettingPreferenceManager
import com.solux.moro.core.navigation.NavGraph
import com.solux.moro.ui.auth.AuthResult
import com.solux.moro.ui.auth.parseAuthResult
import com.solux.moro.ui.notification.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val notificationViewModel: NotificationViewModel by viewModels()
    @Inject lateinit var settingPreferenceManager: SettingPreferenceManager
    private val authResultFlow = MutableStateFlow<AuthResult?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val savedToken = settingPreferenceManager.getAccessToken().orEmpty()
        if (savedToken.isNotBlank()) {
            saveToken(savedToken)
        }
        
        // 앱 실행 시 토큰 확인 및 서버 등록
        getAndRegisterFcmToken()
        handleAuthIntent(intent)
        setContent {
            MoroTheme {
//                MapScreen(
//                    posts = emptyList(),
//                    selectedPost = null,
//                    keyword = "",
//                    onKeywordChange = {},
//                    onSearch = {},
//                    onSelectPost = {},
//                    onClearSelection = {},
//                    onLoadNearby = { _, _ -> },
//                    hasFineLocationPermission = false,
//                    lastKnownLatLng = null,
//                    onLocationPermissionChanged = {},
//                    onUpdateLastKnownLocation = {},
//                )
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    authResultFlow = authResultFlow,
                    onAuthResultConsumed = { authResultFlow.value = null }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAuthIntent(intent)
    }

    private fun handleAuthIntent(intent: Intent?) {
        val uri = intent?.data ?: return
        val result = parseAuthResult(uri) ?: return
        Log.d("AUTH_LOG", "딥링크 수신 uri=$uri")
        Log.d(
            "AUTH_LOG",
            "token=${!result.token.isNullOrBlank()} needsNameSetup=${result.needsNameSetup} tempEmail=${result.tempEmail}"
        )
        if (!result.needsNameSetup) {
            result.token?.let { saveToken(it) }
        }
        authResultFlow.value = result
    }

    private fun saveToken(token: String) {
        val normalized = token.removePrefix("Bearer ").trim()
        if (normalized.isBlank()) {
            Log.w("AUTH_LOG", "토큰 저장 실패: 빈 토큰")
            return
        }
        NetworkModule.token = normalized
        settingPreferenceManager.setAccessToken(normalized)
        Log.d("AUTH_LOG", "토큰 저장 완료: ${normalized.take(12)}...")
    }

    private fun getAndRegisterFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM_LOG", "토큰 발급 실패", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d("FCM_LOG", "현재 FCM 토큰: $token")

            notificationViewModel.registerToken(token)
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoroTheme {
        Greeting("Android")
    }
}
