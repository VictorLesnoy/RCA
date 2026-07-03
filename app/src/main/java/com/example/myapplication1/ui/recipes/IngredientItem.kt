package com.example.myapplication1.ui.recipes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun IngredientItem(
    ingredient: IngredientUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Количество и единицы измерения (например, "2 ст. л.")
        Text(
            text = "${ingredient.quantity} ${ingredient.unitOfMeasure}",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(end = 8.dp)
        )

        // Название ингредиента
        Text(
            text = ingredient.name,
        )
    }
}