package com.solux.moro.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solux.moro.ui.home.HomeScreen
import com.solux.moro.ui.paletteEdit.PaletteEditScreen
import com.solux.moro.ui.profile.ProfileScreen
import com.solux.moro.ui.profile.ProfileViewModel
import com.solux.moro.ui.profileEdit.ProfileEditScreen

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination ="profile_test") {
        composable("home") {
            HomeScreen()
        }
        composable("profile_test") {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(navController, viewModel)
        }

        composable(Profile.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(navController, viewModel)
        }

        composable(
            "profileEdit",
        ) {
            ProfileEditScreen()
        }

        composable(
            "paletteEdit"
        ) {
            PaletteEditScreen()
        }
    }

}
