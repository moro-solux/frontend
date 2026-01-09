package com.solux.moro.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.solux.moro.core.designsystem.theme.MoroTheme
import com.solux.moro.core.navigation.NavGraph
import com.solux.moro.ui.map.MapScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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