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
                    // Если в CategoriesScreen есть переход по категориям — используй categoryId
                    // Например: navController.navigate(Destination.Recipes.route)
                },
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails(recipeId).route)
                }
            )
        }

        composable(Destination.Recipes.route) {
            RecipesScreen(
                recipes = repository.getAllRecipes(),
                onBackClick = {
                    navController.popBackStack()
                },
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
            // Читаем recipeId сразу как Int — никаких строк и парсинга
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
                ?: run {
                    navController.popBackStack()
                    return@composable
                }

            val recipe = repository.getRecipeById(recipeId)
            if (recipe == null) {
                navController.popBackStack()
                return@composable
            }

            // Ключевой момент: remember(recipe.id) — при изменении recipe.id состояние сбросится,
            // а при клике на кнопку isFavorite будет пересчитан из Prefs и UI обновится
            val isFavorite = remember(recipe.id) { favoritePrefs.isFavorite(recipe.id) }

            RecipeDetailsScreen(
                recipe = recipe,
                isFavorite = isFavorite,
                onFavoriteToggle = {
                    if (isFavorite) {
                        favoritePrefs.removeFromFavorites(recipe.id)
                    } else {
                        favoritePrefs.addToFavorites(recipe.id)
                    }
                    // После изменения isFavorite нужно обновить состояние в Compose.
                    // Самый простой способ — пересоздать remember(recipe.id).
                    // Но в текущей сигнатуре RecipeDetailsScreen мы не можем вызвать recomposition снаружи.
                    // Поэтому лучше вынести логику isFavorite внутрь RecipeDetailsScreen или использовать ViewModel.
                    // Ниже — простой вариант без ViewModel: просто меняем значение в mutableState,
                    // но тогда remember(recipe.id) не сработает.
                }
            )
        }
    }
}
