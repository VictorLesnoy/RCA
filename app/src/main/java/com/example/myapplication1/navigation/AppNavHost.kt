package com.example.myapplication1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication1.screens.HomeScreen
import com.example.myapplication1.screens.ProfileScreen
import com.example.myapplication1.screens.SettingsScreen
import com.example.myapplication1.screens.UserDetailScreen
import com.example.myapplication1.screens.ProductScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route
    ) {
        composable(Destinations.Home.route) {
            HomeScreen(navController)
        }

        composable(Destinations.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Destinations.Settings.route) {
            SettingsScreen()
        }

        composable(
            route = "${Destinations.UserDetail.route}/{${Destinations.UserDetail.USER_ID_ARG}}",
            arguments = listOf(
                androidx.navigation.navArgument(Destinations.UserDetail.USER_ID_ARG) {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(Destinations.UserDetail.USER_ID_ARG) ?: "Unknown"
            UserDetailScreen(userId, navController)
        }

        composable(
            route = "${Destinations.Product.route}/{${Destinations.Product.PRODUCT_ID_ARG}}",
            arguments = listOf(
                androidx.navigation.navArgument(Destinations.Product.PRODUCT_ID_ARG) {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString(Destinations.Product.PRODUCT_ID_ARG) ?: "Unknown"
            ProductScreen(productId, navController)
        }
    }
}