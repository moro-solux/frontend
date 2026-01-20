package com.solux.moro.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.solux.moro.R
import com.solux.moro.core.util.noRippleClickable
import com.solux.moro.ui.profile.component.toPxDp
import com.solux.moro.ui.profile.component.toPxSp

@Composable
fun BottomBar(navController: NavHostController? = null) {
    if (navController == null) {
        BottomBarStatic()
        return
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("home", "Home", R.drawable.icon_home),
        BottomNavItem("mission", "Mission", R.drawable.mission),
        BottomNavItem("camera", "Camera", R.drawable.icon_camera),
        BottomNavItem("map", "Map", R.drawable.map),
        BottomNavItem("profile_test", "Profile", R.drawable.profile),
    )

    Column {
        HorizontalDivider(
            thickness = (2.88).toPxDp,
            color = Color(0xFFF2F2F2)
        )
        BottomAppBar(
            containerColor = Color(0xFF121212),
            windowInsets = WindowInsets(0),
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    val tint = if (selected) White else Color(0xFF8E8E8E)

                    Column(
                        Modifier
                            .width((203.60001).toPxDp)
                            .height((166.72).toPxDp)
                            .background(color = Color(0xFF121212))
                            .noRippleClickable {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        verticalArrangement = Arrangement.spacedBy(
                            (11.520000457763672).toPxDp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Icon(
                            painter = painterResource(item.iconRes),
                            modifier = Modifier
                                .padding((0.15429).toPxDp)
                                .width((60.48).toPxDp)
                                .height((69.12).toPxDp),
                            contentDescription = "${item.label} icon",
                            tint = tint
                        )
                        Text(
                            text = item.label,
                            style = TextStyle(
                                fontSize = (40.32).toPxSp,
                                lineHeight = (40.32).toPxSp,
                                fontWeight = FontWeight(400),
                                color = tint,

                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBarStatic() {
    Column {
        HorizontalDivider(
            thickness = (2.88).toPxDp,
            color = Color(0xFFF2F2F2)
        )
        BottomAppBar(
            containerColor = Color(0xFF121212),
            windowInsets = WindowInsets(0),
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                listOf(
                    BottomNavItem("home", "Home", R.drawable.icon_home),
                    BottomNavItem("mission", "Mission", R.drawable.mission),
                    BottomNavItem("camera", "Camera", R.drawable.icon_camera),
                    BottomNavItem("map", "Map", R.drawable.map),
                    BottomNavItem("profile_test", "Profile", R.drawable.profile),
                ).forEach { item ->
                    Column(
                        Modifier
                            .width((203.60001).toPxDp)
                            .height((166.72).toPxDp)
                            .background(color = Color(0xFF121212)),
                        verticalArrangement = Arrangement.spacedBy(
                            (11.520000457763672).toPxDp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(item.iconRes),
                            modifier = Modifier
                                .padding((0.15429).toPxDp)
                                .width((60.48).toPxDp)
                                .height((69.12).toPxDp),
                            contentDescription = "${item.label} icon",
                            tint = White
                        )
                        Text(
                            text = item.label,
                            style = TextStyle(
                                fontSize = (40.32).toPxSp,
                                lineHeight = (40.32).toPxSp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFF2F2F2),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }
        }
    }
}

private data class BottomNavItem(
    val route: String,
    val label: String,
    val iconRes: Int,
)

@Preview (
    device = Devices.PIXEL_4A
)
@Composable
fun BottomBarPreview(){
    BottomBar()
}
