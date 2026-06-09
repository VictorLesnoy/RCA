package com.example.myapplication1.ui.recipes

import androidx.compose.runtime.Immutable
import data.model.IngredientDto

/**
 * Модель данных для отображения ингредиента в UI.
 * Содержит минимальный набор полей, необходимых для визуализации.
 *
 * @param name Название ингредиента
 * @param quantity Количество ингредиента
 * @param unitOfMeasure Единица измерения (кг, шт, ст. л. и т. д.)
 */
@Immutable
data class IngredientUiModel(
    val name: String,
    val quantity: String,
    val unitOfMeasure: String
)

/**
 * Функция‑расширение для преобразования IngredientDto в IngredientUiModel.
 * Выполняет маппинг полей с переименованием description → name.
 *
 * @return IngredientUiModel с преобразованными данными
 */
fun IngredientDto.toUiModel(): IngredientUiModel {
    return IngredientUiModel(
        name = description,           // маппинг description → name
        quantity = quantity,
        unitOfMeasure = unitOfMeasure
    )
}