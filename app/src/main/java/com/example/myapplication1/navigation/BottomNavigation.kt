package com.example.myapplication1.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.navigationBarsPadding

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRecipesClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onCategoriesClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            modifier = Modifier.weight(1f)
                .clip(RoundedCornerShape(50.dp)),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "Категории",
                color = MaterialTheme.colorScheme.onTertiary
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onFavoriteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.weight(1f)
                .clip(RoundedCornerShape(50.dp)),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "Избранное",
                color = MaterialTheme.colorScheme.onError
            )
        }

        Spacer(modifier = Modifier.width(8.dp))  // Отступ между кнопками

        Button(
            onClick = onRecipesClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.weight(1f)
                .clip(RoundedCornerShape(50.dp)),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "Рецепты",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
