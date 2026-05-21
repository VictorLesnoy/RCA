package com.example.myapplication1.ui.categories.model

import androidx.compose.runtime.Immutable
import com.example.myapplication1.ASSETS_URI_PREFIX
import data.model.CategoryDto


/**
 * Модель данных для отображения категории в UI.
 * Содержит минимальный набор полей, необходимых для визуализации.
 *
 * @param id Уникальный идентификатор категории
 * @param title Название категории для отображения
 * @param description Описание категории (может быть сокращено для UI)
 * @param imageUrl URL изображения категории (локальный или сетевой путь)
 */
@Immutable
data class CategoryUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
)

/**
 * Функция‑расширение для преобразования CategoryDto в CategoryUiModel.
 * Выполняет маппинг полей с дополнительной обработкой imageUrl:
 * - если imageUrl начинается с "http", используется как есть;
 * - иначе к нему добавляется префикс ASSETS_URI_PREFIX.
 *
 * @return CategoryUiModel с преобразованными данными
 */
fun CategoryDto.toUiModel(): CategoryUiModel {
    val processedImageUrl = if (imageUrl.startsWith("http", ignoreCase = true)) {
        imageUrl
    } else {
        "$ASSETS_URI_PREFIX$imageUrl"
    }

    return CategoryUiModel(
        id = id,
        title = title, // исправлено: title → title (было: name → title)
        description = description,
        imageUrl = processedImageUrl
    )
}