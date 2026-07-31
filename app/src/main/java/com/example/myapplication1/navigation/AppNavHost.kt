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
                    // Переход к экрану рецептов по выбранной категории
                    navController.navigate(Destination.Recipes.withCategory(categoryId).route)
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
                    navController.navigate(Destination.RecipeDetails(recipeId).route)
                },
                favoritePrefs = favoritePrefs
            )
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = Destination.RecipeDetails.arguments
        ) { backStackEntry ->
            // Читаем recipeId как Int — безопасно, без парсинга строк
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
                ?: run {
                    navController.popBackStack()
                    return@composable
                }

            // Получаем рецепт: если getRecipeById может выбросить исключение — оберни в try/catch в репозитории.
            // Здесь мы ожидаем, что repository.getRecipeById возвращает null, если рецепт не найден.
            val recipeDto = repository.getRecipeById(recipeId)
            if (recipeDto == null) {
                navController.popBackStack()
                return@composable
            }

            // Конвертируем DTO в UI-модель (если ещё не сделано в репозитории)
            val recipe = recipeDto.toRecipeUiModel()

            // ВАЖНО: isFavorite — это mutableState, чтобы UI реагировал на нажатия
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
                    // Обновляем состояние — UI перерисуется и иконка изменится
                    isFavorite = !isFavorite
                }
            )
        }
    }
}