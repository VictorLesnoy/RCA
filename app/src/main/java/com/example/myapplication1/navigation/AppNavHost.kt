package com.example.myapplication1.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.myapplication1.KEY_RECIPE_OBJECT

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
            )
        }

        // Экран рецептов с параметром categoryId
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
                onRecipeClick = { recipeId, recipeModel ->
                    // Сохраняем объект рецепта в savedStateHandle текущего экрана
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = KEY_RECIPE_OBJECT,
                        value = recipeModel
                    )
                    // Навигация на экран деталей с ID рецепта
                    navController.navigate(
                        Destination.RecipeDetail.createRoute(recipeId)
                    )
                }
            )
        }

        // Экран деталей рецепта
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

            // Получаем сохранённый объект рецепта из предыдущего экрана
            val savedRecipe: RecipeUiModel? = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<RecipeUiModel>(KEY_RECIPE_OBJECT)

            // Пытаемся получить рецепт: сначала из кэша, потом из репозитория
            val recipeToDisplay = try {
                savedRecipe ?: RecipesRepositoryStub.getRecipeById(recipeId).toUiModel()
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