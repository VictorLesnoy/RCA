package com.example.myapplication1.ui.categories.model

import com.example.myapplication1.data.model.CategoryDto

/**
 * Преобразует DTO категории в UI‑модель для отображения.
 */
fun CategoryDto.toUiModel(): CategoryUiModel = CategoryUiModel(
    id = this.id,
    imageUrl = this.imageUrl ?: "",
    title = this.name,  // name из DTO → title в UI‑модели
    description = this.description ?: ""  // обработка nullable
)