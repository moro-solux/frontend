package com.solux.moro.core.navigation

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solux.moro.screens.FollowScreen
import com.solux.moro.ui.auth.AuthResult
import com.solux.moro.ui.auth.AuthWebRoute
import com.solux.moro.ui.auth.SignUpRoute
import com.solux.moro.ui.camera.UploadCameraScreen
import com.solux.moro.ui.camera.UploadPostScreen
import com.solux.moro.ui.followlist.FollowRequestScreen
import com.solux.moro.ui.followlist.FollowRequestViewModel
import com.solux.moro.ui.followlist.FollowingViewModel
import com.solux.moro.ui.home.FeedScreen
import com.solux.moro.ui.home.HomeScreen
import com.solux.moro.ui.map.MapScreenRoute
import com.solux.moro.ui.map.MapViewModel
import com.solux.moro.ui.menu.ColorMapEditScreen
import com.solux.moro.ui.menu.ColorMapPostScreen
import com.solux.moro.ui.menu.ColorMapScreen
import com.solux.moro.ui.menu.MenuScreen
import com.solux.moro.ui.mission.MissionScreen
import com.solux.moro.ui.notification.NotificationScreen
import com.solux.moro.ui.notification.NotificationViewModel
import com.solux.moro.ui.onboarding.OnboardingScreen
import com.solux.moro.ui.paletteedit.PaletteEditScreen
import com.solux.moro.ui.profile.ProfileScreen
import com.solux.moro.ui.profile.ProfileViewModel
import com.solux.moro.ui.profilecoloredit.ProfileColorEditScreen
import com.solux.moro.ui.profilecoloredit.ProfileColorEditViewModel
import com.solux.moro.ui.profileedit.ProfileEditScreen
import com.solux.moro.ui.search.SearchUserScreen
import com.solux.moro.ui.search.SearchUserViewModel
import com.solux.moro.ui.splash.SplashScreen
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun NavGraph(
    navController: NavHostController,
    authResultFlow: StateFlow<AuthResult?>? = null,
    onAuthResultConsumed: (() -> Unit)? = null,
    notificationViewModel:NotificationViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
){
    LaunchedEffect(authResultFlow) {
        authResultFlow?.filterNotNull()?.collect { result ->
            if (!result.token.isNullOrBlank() && !result.needsNameSetup) {
                navController.navigate("home") {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                }
            } else if ((result.needsNameSetup || result.token.isNullOrBlank()) &&
                !result.tempEmail.isNullOrBlank()
            ) {
                val encodedEmail = Uri.encode(result.tempEmail)
                navController.navigate("signup?email=$encodedEmail") {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                }
            }
            onAuthResultConsumed?.invoke()
        }
    }

    NavHost(navController = navController, startDestination ="splash") {

        composable("splash") {
            SplashScreen(
                onGoogleStartClick = {
                    navController.navigate("auth_web") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onSkipClick = {
                    navController.navigate("signup?email=") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("auth_web") {
            AuthWebRoute()
        }

        composable(
            route = "signup?email={email}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email").orEmpty()
            SignUpRoute(
                initialEmail = email,
                onClose = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate("onboarding") {
                        popUpTo("signup?email={email}") { inclusive = true }
                    }
                },
                quickStart = false
            )
        }

        composable("onboarding") {
            OnboardingScreen(
                onFinish = {
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }


        // 카메라 화면
        composable("camera") {
            UploadCameraScreen(
                navController = navController,
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
                    navController = navController,
                    capturedUri = uri,
                    onNavigateHome = {
                        // TODO: 완료 후 홈으로
                        navController.popBackStack("camera", inclusive = true)
                    }
                )
            }
        }


        // 미션 메인
        composable("mission") {
            MissionScreen(navController = navController)
        }

        // 미션 카메라
        composable(
            route = "mission_camera/{missionId}",
            arguments = listOf(
                navArgument("missionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val missionId = backStackEntry.arguments?.getLong("missionId") ?: 0L

            com.solux.moro.ui.mission.MissionCameraScreen(
                navController = navController,
                onMissionComplete = { uri ->
                    val encodedUri = Uri.encode(uri.toString())
                    // 촬영 완료 시 업로드 화면으로 이동
                    navController.navigate("mission_upload/$encodedUri/$missionId") {
                        popUpTo("mission_camera/$missionId") { inclusive = true }
                    }
                }
            )
        }

        // 미션 업로드 (이미지 URI + 미션 ID)
        composable(
            route = "mission_upload/{uri}/{missionId}",
            arguments = listOf(
                navArgument("uri") { type = NavType.StringType },
                navArgument("missionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val uriString = backStackEntry.arguments?.getString("uri")
            val missionId = backStackEntry.arguments?.getLong("missionId") ?: 0L
            val uri = uriString?.let { Uri.parse(it) }

            if (uri != null) {
                com.solux.moro.ui.mission.MissionUploadScreen(
                    navController = navController,
                    imageUri = uri,
                    missionId = missionId
                )
            }
        }

        // 내 미션 목록 (My Mission)
        composable("my_mission") {
            com.solux.moro.ui.mission.MyMissionScreen(navController = navController)
        }

        // 미션 상세 (게시물 ID)
        composable(
            route = "mission_post/{misPostId}",
            arguments = listOf(
                navArgument("misPostId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val misPostId = backStackEntry.arguments?.getLong("misPostId") ?: 0L

            // 상세 화면 연결
            com.solux.moro.ui.mission.MissionPostScreen(
                navController = navController,
                misPostId = misPostId
            )
        }



        composable("home") { //홈 화면
            HomeScreen(navController = navController)
        }

        composable("map") {
            val viewModel: MapViewModel = hiltViewModel()
            MapScreenRoute(viewModel = viewModel, navController = navController)
        }
        composable("menu") {
            MenuScreen(navController = navController)
        }
        composable("colormap") {
            ColorMapScreen(navController = navController)
        }

        // 특정 색상 선택 시 상세 페이지 (colorId와 hexCode 전달)
        composable(
            route = "selected_color/{colorId}/{hexCode}",
            arguments = listOf(
                navArgument("colorId") { type = NavType.LongType }, // ID는 Long 타입
                navArgument("hexCode") { type = NavType.StringType } // Hex는 String 타입
            )
        ) { backStackEntry ->
            val colorId = backStackEntry.arguments?.getLong("colorId") ?: 0L
            val hexCode = backStackEntry.arguments?.getString("hexCode") ?: ""

            // 상세 페이지 컴포넌트 연결
            com.solux.moro.ui.menu.SelectedColorScreen(
                colorId = colorId,
                hexCode = hexCode,
                navController = navController
            )
        }


        // 4. 해당 포스트 상세 (SNS 피드 형태)
        composable(
            route = "color_post/{colorId}/{postId}",
            arguments = listOf(
                navArgument("colorId") { type = NavType.LongType },
                navArgument("postId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val colorId = backStackEntry.arguments?.getLong("colorId") ?: 0L
            val postId = backStackEntry.arguments?.getLong("postId") ?: 0L
            ColorMapPostScreen(colorId = colorId, postId = postId, navController = navController)
        }

        // 5. 수정 화면 (대표색상 변경)
        composable(
            route = "postEditScreen/{postId}",
            arguments = listOf(
                navArgument("postId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getLong("postId") ?: 0L
            ColorMapEditScreen(postId = postId, navController = navController)
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

        composable(
            route = FeedRoute.route,
            arguments = listOf(
                navArgument("FeedId") { type = NavType.LongType } // 명시적으로 Long 타입 정의
            )
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getLong("FeedId") ?: return@composable
            FeedScreen(
                navController = navController,
                postId = postId
            )
        }


        composable("profileEdit" ) { // 프로필 설정 화면
            ProfileEditScreen(navController = navController)
        }
        composable("profileColorEdit" ) { // 유저 대표 색 설정 화면
            val viewModel: ProfileColorEditViewModel = hiltViewModel()
            ProfileColorEditScreen(modifier= Modifier,navController = navController,viewModel)
        }

        composable( "paletteEdit" ) { // 팔레트 설정 화면
            PaletteEditScreen(navController = navController)
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
            //val viewModel: NotificationViewModel = hiltViewModel()
            NotificationScreen(
                navController = navController,
                viewModel = notificationViewModel
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
