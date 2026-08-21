package com.example.myapplication1.ui.details

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.myapplication1.util.FavoriteDataStoreManager
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.rememberCoroutineScope
import kotlinx.coroutines.withContext

private val stepRegex = Regex("^\\d+\\.\\s*")

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    onBack: () -> Unit,
) {
    val context: Context = LocalContext.current
    val scope = rememberCoroutineScope()
    val manager = remember { FavoriteDataStoreManager(context) }

    var recipe by remember { mutableStateOf<RecipeUiModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(recipeId) {
        isLoading = true
        error = null

        try {
            val dto = withContext(Dispatchers.IO) {
                RecipesRepositoryStub.getRecipeById(recipeId)
            }

            if (dto == null) {
                error = "Рецепт не найден"
                isLoading = false
                return@LaunchedEffect
            }

            recipe = dto.toUiModel()
            isFavorite = manager.isFavorite(recipeId)

            isLoading = false
        } catch (e: Exception) {
            error = e.message ?: "Ошибка загрузки рецепта"
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (!isLoading && error != null) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "⚠️ ${error}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onBack) {
                    Text("Вернуться назад")
                }
            }
        }
        return
    }

    recipe?.let { currentRecipe ->
        var servings by remember { mutableStateOf(currentRecipe.servings ?: 2) }
        val scaledIngredients = scaleIngredients(currentRecipe.ingredients, servings)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ScreenHeader(
                title = currentRecipe.title,
                imageUrl = currentRecipe.imageUrl,
                showFavoriteButton = true,
                isFavorite = isFavorite,
                onFavoriteToggle = {
                    val newState = !isFavorite
                    isFavorite = newState

                    scope.launch(Dispatchers.IO) {
                        if (newState) {
                            manager.addFavorite(currentRecipe.id)
                        } else {
                            manager.removeFavorite(currentRecipe.id)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PortionsSlider(
                value = servings,
                onValueChange = { servings = it },
                maxServings = currentRecipe.servings?.let { it * 3 } ?: 6,
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
                        modifier = Modifier.padding(
                            start = Dimens.Padding.PaddingMain,
                            end = Dimens.Padding.PaddingMain
                        )
                    )
                }
            }

            Text(
                text = "Приготовление (${currentRecipe.method.size} шагов)",
                modifier = Modifier.padding(top = Dimens.Padding.PaddingLarge, start = Dimens.Padding.PaddingMain),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            currentRecipe.method.forEachIndexed { index, step ->
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
                    val shareIntent = ShareUtils.shareRecipe(context = context, recipeId = currentRecipe.id)
                    try {
                        context.startActivity(Intent.createChooser(shareIntent, "Поделиться рецептом"))
                    } catch (e: Exception) {
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.Padding.PaddingMain)
            ) {
                Text("📤 Поделиться рецептом")
            }
        }
    }
}