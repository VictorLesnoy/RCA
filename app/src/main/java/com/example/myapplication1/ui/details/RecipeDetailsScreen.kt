package com.example.myapplication1.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import coil.compose.AsyncImage
import com.example.myapplication1.ui.recipes.RecipeUiModel
import com.example.myapplication1.ui.recipes.IngredientItem
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(recipe: RecipeUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(enabled = true)
            .padding(bottom = Dimens.Padding.PaddingMain)
    ) {
        ScreenHeader(
            title = recipe.title,
            imageUrl = recipe.imageUrl
        )

        Text(
            text = "Ингредиенты (${recipe.ingredients.size})",
            modifier = Modifier.padding(top = Dimens.Padding.PaddingLarge, start = Dimens.Padding.PaddingMain),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        // Список ингредиентов с разделителями между элементами
        recipe.ingredients.forEachIndexed { index, ingredient ->
            IngredientItem(
                ingredient = ingredient,
                modifier = Modifier.padding(horizontal = Dimens.Padding.PaddingMain)
            )

            // Рисуем разделитель только если это не последний элемент
            if (index < recipe.ingredients.lastIndex) {
                Divider(
                    modifier = Modifier.padding(start = Dimens.Padding.PaddingMain, end = Dimens.Padding.PaddingMain)
                )
            }
        }

        Text(
            text = "Приготовление (${recipe.method.size} шагов)",
            modifier = Modifier.padding(top = Dimens.Padding.PaddingLarge, start = Dimens.Padding.PaddingMain),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        val stepRegex = remember { Regex("^\\d+\\.\\s*") }

        recipe.method.forEachIndexed { index, step ->
            val cleanStep = step.replace(stepRegex, "")
            Text(
                text = "${index + 1}. $cleanStep",
                modifier = Modifier
                    .padding(start = Dimens.Padding.PaddingMain, top = 8.dp, end = Dimens.Padding.PaddingMain)
            )
        }
    }
}

@Composable
private fun ScreenHeader(title: String, imageUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Header image for $title",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))
        Text(
            text = title,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimens.Padding.PaddingMain)
        )
    }
}