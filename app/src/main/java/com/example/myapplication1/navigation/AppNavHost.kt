package com.example.myapplication1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavBackStackEntry
import com.example.myapplication1.screens.CategoriesScreen
import com.example.myapplication1.screens.RecipesScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.Categories.route
    ) {
        // Экран категорий
        composable(Destination.Categories.route) { backStackEntry ->
            CategoriesScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(Destination.Recipes.createRoute(categoryId))
                }
        }

        // Экран рецептов с параметром categoryId
        composable(
            route = "${Destination.Recipes.route}/{${Destination.Recipes.CATEGORY_ID_ARG}}",
            arguments = listOf(
                androidx.navigation.navArgument(Destination.Recipes.CATEGORY_ID_ARG) {
                    type = androidx.navigation.NavType.IntType
                }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt(
                Destination.Recipes.CATEGORY_ID_ARG
            ) ?: 0

            RecipesScreen(
                categoryId = categoryId,
                onBackClick = {
                    navController.popBackStack()
                }
        }
    }
