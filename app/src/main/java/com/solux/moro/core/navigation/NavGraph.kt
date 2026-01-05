package com.solux.moro.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solux.moro.ui.profile.ProfileScreen
import com.solux.moro.ui.profile.ProfileViewModel

@Composable
fun NavGraph(navController: NavHostController){
    val viewModel: ProfileViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
        }
        composable(Profile.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProfileScreen(navController,viewModel)
        }
    }

}
