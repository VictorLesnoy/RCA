package com.example.myapplication1.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication1.ui.components.ScreenHeader
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = Dimens.Padding.PaddingMain,
                end = Dimens.Padding.PaddingMain,
                bottom = Dimens.Padding.PaddingLarge
            )
    ) {
        // Заголовок экрана с использованием общего компонента
        ScreenHeader(
            imagePainter = null, // Фоновое изображение не требуется для этой заглушки
            contentDescription = "Заголовок экрана рецептов",
            title = "Рецепты"
        )

        // Временный текст-заглушка
        Text(
            text = "Скоро здесь будет список рецептов",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(top = Dimens.Padding.PaddingLarge)
                .fillMaxSize()
        )
    }
}