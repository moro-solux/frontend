package com.solux.moro.core.navigation

sealed interface Route{
    val route: String
}

sealed interface MainTabRoute : Route

//@Serializable
//data object Home : MainTabRoute
//
//@Serializable
//data object Pay : MainTabRoute
//
//@Serializable
//data object Order : MainTabRoute
//
//@Serializable
//data object Shop : MainTabRoute
//
//@Serializable
//data object Other : MainTabRoute
//
//@Serializable
//data class MyMenu(
//    val menuId: Long
//) : Route
data object Profile : Route {
    override val route = "profile/{userId}"

    fun createRoute(userId: String): String =
        "profile/$userId"
}
