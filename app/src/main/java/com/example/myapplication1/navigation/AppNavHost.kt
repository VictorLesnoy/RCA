package com.example.myapplication1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication1.screens.CategoriesScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.data.FavoritePrefsManager
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.navigation.Destination

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
                    navController.navigate(Destination.RecipeDetails.routeWithId(recipe.id))
                },
                onCategoryClick = { categoryId ->
                    val route = Destination.Recipes.route
                    // Если categoryId null — передаём маршрут без параметра (будет default=null)
                    val finalRoute = if (categoryId == null) route else "$route?categoryId=$categoryId"
                    navController.navigate(finalRoute)
                }
            )
        }

        composable(
            route = Destination.Recipes.route,
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.IntType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId")
            val recipes = repository.getAllRecipesByCategory(categoryId)

            RecipesScreen(
                recipes = recipes,
                onRecipeClick = { recipe ->
                    navController.navigate(Destination.RecipeDetails.routeWithId(recipe.id))
                },
                favoritePrefs = favoritePrefs
            )
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: return@composable
            val recipe = repository.getRecipeById(recipeId)

            if (recipe != null) {
                RecipeDetailsScreen(
                    recipe = recipe,
                    isFavorite = favoritePrefs.isFavorite(recipe.id),
                    onFavoriteToggle = {
                        favoritePrefs.toggleFavorite(recipe.id)
                    }
                )
            } else {
                androidx.compose.foundation.layout.Column(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    androidx.compose.material3.Text(
                        text = "Рецепт не найден",
                        style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}