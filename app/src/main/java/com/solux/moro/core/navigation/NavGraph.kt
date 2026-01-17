package com.solux.moro.core.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solux.moro.screens.FollowScreen
import com.solux.moro.ui.camera.UploadCameraScreen
import com.solux.moro.ui.camera.UploadPostScreen
import com.solux.moro.ui.followlist.FollowRequestScreen
import com.solux.moro.ui.followlist.FollowRequestViewModel
import com.solux.moro.ui.followlist.FollowingViewModel
import com.solux.moro.ui.home.HomeScreen
import com.solux.moro.ui.menu.ColorMapScreen
import com.solux.moro.ui.menu.MenuScreen
import com.solux.moro.ui.notification.NotificationScreen
import com.solux.moro.ui.notification.NotificationViewModel
import com.solux.moro.ui.paletteedit.PaletteEditScreen
import com.solux.moro.ui.profile.ProfileScreen
import com.solux.moro.ui.profile.ProfileViewModel
import com.solux.moro.ui.profilecoloredit.ProfileColorEditScreen
import com.solux.moro.ui.profilecoloredit.ProfileColorEditViewModel
import com.solux.moro.ui.profileedit.ProfileEditScreen
import com.solux.moro.ui.search.SearchUserScreen
import com.solux.moro.ui.search.SearchUserViewModel

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination ="camera") {

        // 카메라 화면
        composable("camera") {
            UploadCameraScreen(
                onNavigateToPost = { uri ->
                    val encodedUri = Uri.encode(uri.toString())
                    navController.navigate("post/$encodedUri")
                }
            )
        }

        // 업로드 화면
        composable(
            route = "post/{uri}",
            arguments = listOf(
                navArgument("uri") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val uriString = backStackEntry.arguments?.getString("uri")

            val uri = uriString?.let { Uri.parse(it) }

            if (uri != null) {
                UploadPostScreen(
                    capturedUri = uri,
                    onNavigateHome = {
                        // TODO: 완료 후 홈으로
                        navController.popBackStack("camera", inclusive = true)
                    }
                )
            }
        }

        composable("home") { //홈 화면
            HomeScreen()
        }
        composable("menu") {
            MenuScreen(navController = navController)
        }
        composable("colormap") {
            ColorMapScreen(navController = navController)
        }

        composable("profile_test") {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(navController, viewModel)
        }

        composable(Profile.route, // 각 유저 프로필 화면
        ) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(navController, viewModel)
        }

        composable("profileEdit" ) { // 프로필 설정 화면
            ProfileEditScreen(navController = navController)
        }
        composable("profileColorEdit" ) { // 유저 대표 색 설정 화면
            val viewModel: ProfileColorEditViewModel = hiltViewModel()
            ProfileColorEditScreen(modifier= Modifier,navController = navController,viewModel)
        }

        composable( "paletteEdit" ) { // 팔레트 설정 화면
            PaletteEditScreen()
        }

        composable( route = "follow", // 팔로우/팔로잉 화면
            ) {
            val viewModel: FollowingViewModel = hiltViewModel()
            FollowScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable( route = "follow_request", // 팔로우 화면
        ) {
            val viewModel: FollowRequestViewModel = hiltViewModel()
            FollowRequestScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable( "notification" ){ // 알림 화면
            val viewModel: NotificationViewModel = hiltViewModel()
            NotificationScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("search") {
            val viewModel: SearchUserViewModel = hiltViewModel()
            SearchUserScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }

}
