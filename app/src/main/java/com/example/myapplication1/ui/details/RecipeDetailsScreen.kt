package com.example.myapplication1.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication1.ui.recipes.RecipeUiModel
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.Padding.PaddingMain)
    ) {
        // Название рецепта
        Text(
            text = "Название: ${recipe.title}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // ID рецепта
        Text(
            text = "ID: ${recipe.id}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // URL изображения
        Text(
            text = "Изображение: ${recipe.imageUrl}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )


        // Статус «Избранное»
        Text(
            text = "Избранное: ${if (recipe.isFavorite) "Да" else "Нет"}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // Количество ингредиентов
        Text(
            text = "Количество ингредиентов: ${recipe.ingredients.size}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )

        // Количество шагов приготовления
        Text(
            text = "Шагов приготовления: ${recipe.method.size}",
            modifier = Modifier.padding(bottom = Dimens.Padding.PaddingSmall)
        )
    }
}