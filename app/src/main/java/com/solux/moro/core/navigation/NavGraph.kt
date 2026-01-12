package com.solux.moro.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.solux.moro.screens.FollowScreen
import com.solux.moro.ui.home.HomeScreen
import com.solux.moro.ui.notification.NotificationScreen
import com.solux.moro.ui.paletteedit.PaletteEditScreen
import com.solux.moro.ui.profile.ProfileScreen
import com.solux.moro.ui.profile.ProfileViewModel
import com.solux.moro.ui.profileedit.ProfileEditScreen

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination ="notification") {
        composable("home") {
            HomeScreen()
        }
        composable("profile_test") {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(navController, viewModel)
        }

        composable(Profile.route,
        ) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(navController, viewModel)
        }

        composable("profileEdit" ) {
            ProfileEditScreen(navController = navController)
        }

        composable( "paletteEdit" ) {
            PaletteEditScreen()
        }

        composable( route = "follow",
            ) {
            //val viewModel: FollowingViewModel = hiltViewModel()
            FollowScreen(
                navController = navController,
                //viewModel = viewModel
            )
        }

        composable( "notification" ){
            //val viewModel: NotificationViewModel = hiltViewModel()
            NotificationScreen(
                navController = navController,
                //viewModel = viewModel
                )
        }
    }

}
