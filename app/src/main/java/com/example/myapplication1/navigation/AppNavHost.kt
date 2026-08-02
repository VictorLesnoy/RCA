package com.example.myapplication1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication1.navigation.Destination
import com.example.myapplication1.ui.categories.CategoriesScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.utils.FavoritePrefsManager

@Composable
fun AppNavHost(
    navController: androidx.navigation.NavController,
    repository: RecipesRepositoryStub,
    favoritePrefs: FavoritePrefsManager
) {
    NavHost(navController = navController, startDestination = Destination.Categories.route) {

        composable(Destination.Categories.route) {
            CategoriesScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(Destination.Recipes.routeWithCategory(categoryId))
                }
            )
        }

        composable(
            route = Destination.Recipes.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId")
                ?: run {
                    navController.popBackStack()
                    return@composable
                }

            RecipesScreen(
                categoryId = categoryId,
                onBackClick = {
                    navController.popBackStack()
                },
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails.routeWithId(recipeId))
                },
                favoritePrefs = favoritePrefs
            )
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = Destination.RecipeDetails.arguments
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
                ?: run {
                    navController.popBackStack()
                    return@composable
                }

            val recipeDto = repository.getRecipeById(recipeId)

            if (recipeDto == null) {
                navController.popBackStack()
                return@composable
            }

            val recipe = recipeDto.toUiModel()

            var isFavorite by rememberSaveable(key = recipe.id) {
                mutableStateOf(favoritePrefs.isFavorite(recipe.id))
            }

            RecipeDetailsScreen(
                recipe = recipe,
                isFavorite = isFavorite,
                onFavoriteToggle = {
                    if (isFavorite) {
                        favoritePrefs.removeFromFavorites(recipe.id)
                    } else {
                        favoritePrefs.addToFavorites(recipe.id)
                    }
                    isFavorite = !isFavorite
                }
            )
        }
    }
}