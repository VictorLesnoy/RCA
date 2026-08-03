package com.example.myapplication1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication1.ui.categories.CategoriesScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.data.FavoritePrefsManager
import com.example.myapplication1.ui.recipes.RecipeUiModel
import com.example.myapplication1.utils.RecipesRepositoryStub

@Composable
fun AppNavHost(
    navController: androidx.navigation.NavController,
    favoritePrefs: FavoritePrefsManager,
    repository: RecipesRepositoryStub
) {
    NavHost(navController = navController, startDestination = Destination.Categories.route) {

        composable(Destination.Categories.route) {
            CategoriesScreen(
                onRecipeClick = { recipe ->
                    navController.navigate(
                        Destination.RecipeDetails.routeWithId(recipe.id)
                    )
                },
                onCategoryClick = { categoryId ->
                    val route = Destination.Recipes.route
                    navController.navigate("$route?categoryId=$categoryId")
                }
            )
        }

        composable(
            route = Destination.Recipes.route,
            arguments = listOf(
                navArgument("categoryId") {
                    type = androidx.navigation.NavType.IntType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId")

            RecipesScreen(
                recipes = repository.getAllRecipes(categoryId),
                onRecipeClick = { recipe ->
                    navController.navigate(
                        Destination.RecipeDetails.routeWithId(recipe.id)
                    )
                },
                favoritePrefs = favoritePrefs
            )
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(
                navArgument("recipeId") { type = androidx.navigation.NavType.IntType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            val recipe = repository.getRecipeById(recipeId)

            // Если рецепт не найден — можно сделать отдельный экран ошибки или просто пустой
            recipe?.let { r ->
                RecipeDetailsScreen(
                    recipe = r,
                    isFavorite = favoritePrefs.isFavorite(r.id),
                    onFavoriteToggle = {
                        favoritePrefs.toggleFavorite(r.id)
                    }
                )
            } ?: run {
                // Заглушка, если рецепт не найден
                androidx.compose.foundation.Text("Рецепт не найден")
            }
        }
    }
}