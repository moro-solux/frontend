package com.solux.moro.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solux.moro.ui.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {

        }
        composable(Profile.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProfileScreen()
        }
    }

}
