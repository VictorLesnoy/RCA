package com.example.myapplication1.ui.recipes

import kotlin.math.round

/**
 * Парсит строку количества в Double.
 * Поддерживает форматы: "2", "2.5", "3,5" (заменяем запятую на точку).
 */
private fun parseQuantity(quantityStr: String): Double? {
    val normalized = quantityStr.replace(',', '.')
    return normalized.toDoubleOrNull()
}

/**
 * Форматирует число обратно в строку:
 * - 1.0 -> "1"
 * - 0.5 -> "0.5"
 * - 2.333 -> "2.3" (округляем до 1 знака)
 */
private fun formatQuantity(value: Double): String {
    // Округляем до 1 знака после запятой
    val rounded = round(value * 10.0) / 10.0
    // Если дробная часть 0, возвращаем как целое
    return if (rounded % 1.0 == 0.0) {
        rounded.toInt().toString()
    } else {
        rounded.toString()
    }
}

fun scaleIngredients(
    ingredients: List<IngredientUiModel>,
    servings: Int,
    baseServings: Int
): List<IngredientUiModel> {
    if (servings == baseServings || ingredients.isEmpty()) {
        return ingredients
    }

    val factor = servings.toFloat() / baseServings.toFloat()

    return ingredients.map { ingredient ->
        val originalValue = parseQuantity(ingredient.quantity)

        if (originalValue == null) {
            // Если не удалось распарсить (например, "по вкусу"), возвращаем как есть
            ingredient
        } else {
            val scaledValue = originalValue * factor
            ingredient.copy(
                quantity = formatQuantity(scaledValue)
            )
        }
    }
}