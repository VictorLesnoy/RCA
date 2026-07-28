package com.example.myapplication1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.ui.components.ScreenHeader
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.utils.FavoritePrefsManager
import com.example.myapplication1.ui.theme.MyApplication1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.ui.platform.LocalContext

sealed class Destination(val route: String) {
    object Recipes : Destination("recipes")
    data class RecipeDetails(val id: Int) : Destination("recipe/{id}")
}

@Composable
fun AppNavHost(
    navController: NavController,
    coroutineScope: CoroutineScope,
    repository: RecipesRepositoryStub
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Recipes.route
    ) {
        composable(Destination.Recipes.route) {
            MyApplication1Theme {
                RecipesScreen(
                    recipes = repository.getAllRecipes(),
                    onRecipeClick = { recipeId ->
                        navController.navigate(Destination.RecipeDetails(recipeId).route)
                    }
                )
            }
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(
                androidx.navigation.navArgument("id") { type = androidx.navigation.NavType.IntType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("id") ?: return@composable

            // Получаем рецепт из репозитория
            var recipe by androidx.compose.runtime.remember {
                mutableStateOf<RecipeUiModel?>(null)
            }

            // Загружаем рецепт асинхронно
            androidx.compose.runtime.LaunchedEffect(recipeId) {
                withContext(Dispatchers.IO) {
                    val loadedRecipe = repository.getRecipeById(recipeId)
                    withContext(Dispatchers.Main) {
                        recipe = loadedRecipe
                    }
                }
            }

            if (recipe == null) {
                // Можно показать индикатор загрузки
                androidx.compose.foundation.layout.Box(
                    modifier = androidx.compose.foundation.layout.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.CircularProgressIndicator()
                }
                return@composable
            }

            // Менеджер избранного (через SharedPreferences)
            val favoriteManager = FavoritePrefsManager(LocalContext.current)

            // Проверяем, добавлен ли рецепт в избранное
            var isFavorite by androidx.compose.runtime.remember(recipeId) {
                mutableStateOf(favoriteManager.isFavorite(recipeId))
            }

            // Обработчик переключения избранного
            val onFavoriteToggle = {
                if (isFavorite) {
                    favoriteManager.removeFromFavorites(recipeId)
                } else {
                    favoriteManager.addToFavorites(recipe)
                }
                isFavorite = !isFavorite
            }

            MyApplication1Theme {
                RecipeDetailsScreen(
                    recipe = recipe,
                    isFavorite = isFavorite,
                    onFavoriteToggle = onFavoriteToggle
                )
            }
        }
    }
}
