package com.example.myapplication1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArguments
import com.example.myapplication1.navigation.Destination
import com.example.myapplication1.ui.components.ScreenHeader // если понадобится
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.categories.CategoriesScreen
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.utils.FavoritePrefsManager

@Composable
fun AppNavHost(
    navController: NavController,
    repository: RecipesRepositoryStub,
    favoritePrefs: FavoritePrefsManager
) {
    NavHost(navController = navController, startDestination = Destination.Categories.route) {

        composable(Destination.Categories.route) {
            CategoriesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails(recipeId).route)
                }
            )
        }

        composable(Destination.Recipes.route) {
            RecipesScreen(
                recipes = repository.getAllRecipes(),
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails(recipeId).route)
                },
                favoritePrefs = favoritePrefs
            )
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = Destination.RecipeDetails.arguments
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: return@composable

            // Получаем рецепт из репозитория (в стабе это синхронный getRecipeById)
            val recipe = repository.getRecipeById(recipeId)
            if (recipe == null) {
                // Можно показать пустой экран или сразу назад
                navController.popBackStack()
                return@composable
            }

            // Состояние избранного: берём из Prefs, но держим в mutableState для UI
            val isFavorite = favoritePrefs.isFavorite(recipe.id)

            RecipeDetailsScreen(
                recipe = recipe,
                isFavorite = isFavorite,
                onFavoriteToggle = {
                    if (isFavorite) {
                        favoritePrefs.removeFromFavorites(recipe.id)
                    } else {
                        favoritePrefs.addToFavorites(recipe.id)
                    }
                }
            )
        }
    }
}