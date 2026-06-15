package com.example.myapplication1.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication1.model.RecipeUiModel
import com.example.myapplication1.ui.theme.Dimens
import com.example.myapplication1.KEY_RECIPE_OBJECT
import com.example.myapplication1.data.repository.RecipesRepositoryStub

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    navController: NavController
) {
    // Получаем сохранённый объект рецепта из предыдущего экрана
    val savedRecipe: RecipeUiModel? = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<RecipeUiModel>(KEY_RECIPE_OBJECT)

    // Используем либо сохранённый объект, либо загружаем по ID
    val recipeToDisplay = savedRecipe ?: loadRecipeById(recipeId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Padding.PaddingMain)
    ) {
        // Название рецепта
        Text(
            text = "Название: ${recipeToDisplay.title}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // ID рецепта (для проверки передачи объекта)
        Text(
            text = "ID: ${recipeToDisplay.id}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // URL изображения
        Text(
            text = "Изображение: ${recipeToDisplay.imageUrl}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // Время приготовления
        Text(
            text = "Время приготовления: ${recipeToDisplay.cookingTime} мин.",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // Статус «Избранное»
        Text(
            text = "Избранное: ${if (recipeToDisplay.isFavorite) "Да" else "Нет"}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // Количество ингредиентов
        Text(
            text = "Количество ингредиентов: ${recipeToDisplay.ingredients.size}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // Количество шагов приготовления
        Text(
            text = "Шагов приготовления: ${recipeToDisplay.method.size}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )
    }
}

// Вспомогательная функция загрузки рецепта по ID
private fun loadRecipeById(recipeId: Int): RecipeUiModel {
    return RecipesRepositoryStub.getRecipeById(recipeId).toUiModel()
}
