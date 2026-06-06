package com.example.myapplication1.ui.recipes

import androidx.compose.runtime.Immutable
import com.example.myapplication1.ASSETS_URI_PREFIX
import data.model.RecipeDto

/**
 * Модель данных для отображения рецепта в UI.
 * Содержит минимальный набор полей, необходимых для визуализации.
 *
 * @param id Уникальный идентификатор рецепта
 * @param title Название рецепта
 * @param imageUrl URL изображения рецепта (локальный или сетевой путь)
 * @param ingredients Список ингредиентов
 * @param method Пошаговая инструкция приготовления
 * @param isFavorite Флаг «Избранное» (изначально false)
 */
@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val isFavorite: Boolean = false
)

/**
 * Функция‑расширение для преобразования RecipeDto в RecipeUiModel.
 * Выполняет маппинг полей с дополнительной обработкой imageUrl:
 * - если imageUrl начинается с "http", используется как есть;
 * - иначе к нему добавляется префикс ASSETS_URI_PREFIX.
 * Поле isFavorite инициализируется как false.
 *
 * @return RecipeUiModel с преобразованными данными
 */
fun RecipeDto.toUiModel(): RecipeUiModel {
    val processedImageUrl = if (imageUrl.startsWith("http", ignoreCase = true)) {
        imageUrl
    } else {
        "$ASSETS_URI_PREFIX$imageUrl"
    }

    return RecipeUiModel(
        id = id,
        title = title,
        imageUrl = processedImageUrl,
        ingredients = ingredients.map { it.toUiModel() },
        method = method,
        isFavorite = false
    )
}