package com.example.myapplication1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavBackStackEntry
import com.example.myapplication1.screens.CategoriesScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.recipes.RecipeUiModel
import com.example.myapplication1.KEY_RECIPE_OBJECT
import com.example.myapplication1.data.repository.RecipesRepositoryStub

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
    }

    // Экран деталей рецепта
    composable(
        route = Destination.RecipeDetail.route,
        arguments = listOf(
            androidx.navigation.navArgument(Destination.RecipeDetail.RECIPE_ID_ARG) {
                type = androidx.navigation.NavType.IntType
            }
        )
    ) { backStackEntry: NavBackStackEntry ->
        val recipeId = backStackEntry.arguments?.getInt(
            Destination.RecipeDetail.RECIPE_ID_ARG
        ) ?: 0

        // Получаем сохранённый объект рецепта из предыдущего экрана
        val savedRecipe: RecipeUiModel? = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<RecipeUiModel>(Destination.RecipeDetail.KEY_RECIPE_OBJECT)

        // Используем либо сохранённый объект, либо загружаем по ID
        val recipeToDisplay = savedRecipe ?: loadRecipeById(recipeId)

        RecipeDetailsScreen(recipe = recipeToDisplay)
    }
}

// Вспомогательная функция загрузки рецепта по ID
private fun loadRecipeById(recipeId: Int): RecipeUiModel {
    return RecipesRepositoryStub.getRecipeById(recipeId).toUiModel()
}