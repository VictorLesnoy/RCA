package com.example.myapplication1.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.myapplication1.R
import com.example.myapplication1.ui.components.ScreenHeader
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun FavoritesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = Dimens.Padding.PaddingMain,
                end = Dimens.Padding.PaddingMain,
                bottom = Dimens.Padding.PaddingLarge
            )
    ) {
        // Заголовок экрана с использованием общего компонента
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.bcg_favorites),
            contentDescription = "Фон экрана избранного",
            title = "Избранное"
        )

        // Используем список заглушек
        FavoritesListPlaceholder()
    }
}

@Composable
private fun FavoritesListPlaceholder() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding.PaddingMedium)
    ) {
        repeat(5) { index ->
            FavoriteRecipePlaceholder(position = index + 1)
        }
    }
}

@Composable
private fun FavoriteRecipePlaceholder(position: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.Shadows.ElevationMedium)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Padding.PaddingMain),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Рецепт №$position",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
