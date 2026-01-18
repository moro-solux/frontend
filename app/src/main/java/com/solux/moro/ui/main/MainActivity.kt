package com.solux.moro.ui.main

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
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.navigation.NavGraph
import com.solux.moro.ui.notification.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
            // 앱 실행 시 토큰 확인 및 서버 등록
            getAndRegisterFcmToken()
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
                NavGraph(navController = navController)
            }
        }
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