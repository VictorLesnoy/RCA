package com.example.myapplication1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication1.screens.CategoriesScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.data.FavoritePrefsManager
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.navigation.Destination
import kotlin.coroutines.CoroutineContext

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
                    kotlinx.coroutines.launch {
                        delay(100)
                        navController.navigate(Destination.RecipeDetails.routeWithId(recipe.id))
                    }
                },
                onCategoryClick = { categoryId ->
                    val route = Destination.Recipes.route
                    val finalRoute = if (categoryId == null) route else "$route?categoryId=$categoryId"
                    kotlinx.coroutines.launch {
                        delay(100)
                        navController.navigate(finalRoute)
                    }
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
            val recipes = repository.getRecipesByCategoryId(categoryId)

            RecipesScreen(
                recipes = recipes,
                onRecipeClick = { recipe ->
                    kotlinx.coroutines.launch {
                        delay(100)
                        navController.navigate(Destination.RecipeDetails.routeWithId(recipe.id))
                    }
                }
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
                var isFavorite by remember(recipe.id) {
                    mutableStateOf(favoritePrefs.isFavorite(recipe.id))
                }

                RecipeDetailsScreen(
                    recipe = recipe,
                    isFavorite = isFavorite,
                    onFavoriteToggle = {
                        favoritePrefs.toggleFavorite(recipe.id)
                        isFavorite = favoritePrefs.isFavorite(recipe.id)
                    }
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Рецепт не найден",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}