package com.example.practico4.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practico4.ui.screens.map.MapScreen
import com.example.practico4.ui.screens.routes.RoutesScreen
import com.example.practico4.ui.screens.splash.SplashScreen


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Routes : Screen("routes/{user}") {
        fun go(user: String) = "routes/$user"
    }
    object Map : Screen("map/{id}") {
        fun go(id: Int) = "map/$id"
    }
}


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {


        composable(Screen.Splash.route) {
            SplashScreen { user ->
                navController.navigate(Screen.Routes.go(user))
            }
        }
        composable(
            route = Screen.Routes.route,
            arguments = listOf(
                navArgument("user") { defaultValue = "" }
            )
        ) { backStackEntry ->

            val user = backStackEntry.arguments?.getString("user") ?: ""

            RoutesScreen(
                username = user,
                onOpenRoute = { route ->
                    navController.navigate(Screen.Map.go(route.id!!))
                }
            )
        }
        composable(
            route = Screen.Map.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0
            MapScreen(id)
        }
    }
}
