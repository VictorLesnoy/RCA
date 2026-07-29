package com.example.myapplication1.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.ui.components.ScreenHeader
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.utils.FavoritePrefsManager
import com.example.myapplication1.ui.theme.MyApplication1Theme
import com.example.myapplication1.ui.recipes.RecipeUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class Destination(val route: String) {
    object Recipes : Destination("recipes")
    data class RecipeDetails(val id: Int) : Destination("recipe/$id") // динамический route с реальным ID
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
                        // Теперь будет переходить на реальный route, например: recipe/123
                        navController.navigate(Destination.RecipeDetails(recipeId).route)
                    }
                )
            }
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("id") ?: return@composable

            var recipe by remember {
                mutableStateOf<RecipeUiModel?>(null)
            }

            // Асинхронная загрузка рецепта
            androidx.compose.runtime.LaunchedEffect(recipeId) {
                withContext(Dispatchers.IO) {
                    val loadedRecipe = repository.getRecipeById(recipeId)
                    withContext(Dispatchers.Main) {
                        recipe = loadedRecipe
                    }
                }
            }

            if (recipe == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                return@composable
            }

            // FavoritePrefsManager создаётся один раз на экран (через remember)
            val favoriteManager = remember(recipeId) {
                FavoritePrefsManager(LocalContext.current)
            }

            // Состояние избранного запоминается по recipeId
            var isFavorite by remember(recipeId) {
                mutableStateOf(favoriteManager.isFavorite(recipeId))
            }

            val onFavoriteToggle = {
                if (isFavorite) {
                    favoriteManager.removeFromFavorites(recipeId)
                } else {
                    // Передаём recipeId, а не recipe — сигнатура addToFavorites(recipeId: Int)
                    favoriteManager.addToFavorites(recipeId)
                }
                isFavorite = !isFavorite
            }

            MyApplication1Theme {
                RecipeDetailsScreen(
                    recipe = recipe,          // recipe гарантированно не null здесь
                    isFavorite = isFavorite,
                    onFavoriteToggle = onFavoriteToggle
                )
            }
        }
    }
}