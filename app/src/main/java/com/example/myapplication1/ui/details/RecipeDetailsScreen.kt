package com.example.myapplication1.ui.details

import android.content.Context
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.ContentScale
import com.example.myapplication1.R
import com.example.myapplication1.ui.components.ScreenHeader
import com.example.myapplication1.ui.recipes.IngredientItem
import com.example.myapplication1.ui.recipes.RecipeUiModel
import com.example.myapplication1.ui.theme.Dimens
import com.example.myapplication1.ui.recipes.scaleIngredients
import com.example.myapplication1.utils.ShareUtils
import androidx.compose.foundation.rememberScrollState

private val stepRegex = Regex("^\\d+\\.\\s*")

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context: Context = LocalContext.current

    // Сохраняем порции по ID рецепта — при переходе на другой рецепт значение сбросится
    var servings by rememberSaveable(key = recipe.id) {
        mutableStateOf(recipe.servings ?: 1)
    }

    val scaledIngredients = remember(recipe.ingredients, servings) {
        scaleIngredients(
            ingredients = recipe.ingredients,
            servings = servings,
            baseServings = recipe.servings ?: 1
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .padding(bottom = Dimens.Padding.PaddingMain)
    ) {
        ScreenHeader(
            title = recipe.title,
            imageUrl = recipe.imageUrl,
            showFavoriteButton = true,
            isFavorite = isFavorite,
            onFavoriteToggle = onFavoriteToggle
        )

        Spacer(modifier = Modifier.height(16.dp))

        PortionsSlider(
            value = servings,
            onValueChange = { servings = it },
            maxServings = recipe.servings?.let { it * 3 } ?: 6,
            modifier = Modifier.padding(horizontal = Dimens.Padding.PaddingMain)
        )

        Text(
            text = "Ингредиенты (${scaledIngredients.size})",
            modifier = Modifier.padding(top = Dimens.Padding.PaddingLarge, start = Dimens.Padding.PaddingMain),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        scaledIngredients.forEachIndexed { index, ingredient ->
            IngredientItem(
                ingredient = ingredient,
                modifier = Modifier.padding(horizontal = Dimens.Padding.PaddingMain)
            )

            if (index < scaledIngredients.lastIndex) {
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

        recipe.method.forEachIndexed { index, step ->
            val cleanStep = step.replace(stepRegex, "")
            Text(
                text = "${index + 1}. $cleanStep",
                modifier = Modifier
                    .padding(start = Dimens.Padding.PaddingMain, top = 8.dp, end = Dimens.Padding.PaddingMain)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val shareIntent = ShareUtils.shareRecipe(context = context, recipeId = recipe.id)
                context.startActivity(Intent.createChooser(shareIntent, "Поделиться рецептом"))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Padding.PaddingMain)
        ) {
            Text("📤 Поделиться рецептом")
        }
    }
}