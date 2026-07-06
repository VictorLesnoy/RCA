package com.example.myapplication1.navigation

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.screens.CategoriesScreen
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.ui.recipes.RecipeUiModel
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.recipes.toUiModel

@Composable
fun AppNavHost(
    deepLinkIntent: Intent? = null,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    LaunchedEffect(deepLinkIntent) {
        val uri = deepLinkIntent?.data ?: return@LaunchedEffect

        val pathSegments = uri.pathSegments

        if (pathSegments.size >= 2 && pathSegments[0] == "recipe") {
            val recipeIdString = pathSegments[1]
            val recipeId = recipeIdString.toIntOrNull()

            if (recipeId != null) {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                if (currentRoute != Destination.RecipeDetail.route) {
                    navController.navigate(Destination.RecipeDetail.createRoute(recipeId))
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Destination.Categories.route,
        modifier = modifier
    ) {

        composable(Destination.Categories.route) { backStackEntry ->
            CategoriesScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(Destination.Recipes.createRoute(categoryId))
                }
            )
        }

        composable(
            route = Destination.Recipes.route,
            arguments = listOf(
                navArgument(Destination.Recipes.CATEGORY_ID_ARG) {
                    type = NavType.IntType
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
                },
                onRecipeClick = { recipeId, _ ->
                    navController.navigate(
                        Destination.RecipeDetail.createRoute(recipeId)
                    )
                }
            )
        }

        composable(
            route = Destination.RecipeDetail.route,
            arguments = listOf(
                navArgument(Destination.RecipeDetail.RECIPE_ID_ARG) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt(
                Destination.RecipeDetail.RECIPE_ID_ARG
            ) ?: 0

            val recipeToDisplay = try {
                RecipesRepositoryStub.getRecipeById(recipeId).toUiModel()
            } catch (e: IllegalArgumentException) {
                null
            }

            if (recipeToDisplay != null) {
                RecipeDetailsScreen(recipe = recipeToDisplay)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Рецепт с ID $recipeId не найден",
                        color = Color.Red
                    )
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Назад")
                    }
                }
            }
        }
    }
}