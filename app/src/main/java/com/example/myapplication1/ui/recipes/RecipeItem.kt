package com.example.myapplication1.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import coil.compose.SubcomposeAsyncImage
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun RecipeItem(
    recipe: RecipeUiModel,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.Padding.PaddingSmall)
            .clickable { onRecipeClick(recipe.id) }
    ) {
        Column(modifier = Modifier.padding(Dimens.Padding.PaddingMedium)) {
            SubcomposeAsyncImage(
                model = recipe.imageUrl,
                contentDescription = "Изображение рецепта ${recipe.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.Heights.ListItem),
                loading = {
                    CircularProgressIndicator()
                },
                error = { Text(text = "Не удалось загрузить изображение", color = Color.Gray) }
            )

            Spacer(modifier = Modifier.height(Dimens.Padding.PaddingSmall))

            Text(text = recipe.title)
        }
    }
}