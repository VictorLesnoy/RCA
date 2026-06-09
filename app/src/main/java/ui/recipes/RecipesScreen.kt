package com.example.myapplication1.ui.recipes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.ui.components.ScreenHeader
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun RecipesScreen(
    categoryId: Int,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(categoryId) {
        val recipeDtos = RecipesRepositoryStub.getRecipesByCategoryId(categoryId)
        recipes = recipeDtos.map { it.toUiModel() }
        isLoading = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = Dimens.Padding.PaddingMain,
                end = Dimens.Padding.PaddingMain,
                bottom = Dimens.Padding.PaddingLarge
            )
    ) {
        ScreenHeader(
            contentDescription = "Заголовок экрана рецептов",
            title = "Рецепты"
        )

        if (isLoading) {
            Text(
                text = "Загрузка...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = Dimens.Padding.PaddingLarge)
                    .fillMaxSize()
            )
        } else if (recipes.isEmpty()) {
            Text(
                text = "Рецепты не найдены",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = Dimens.Padding.PaddingLarge)
                    .fillMaxSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onRecipeClick = onRecipeClick
                    )
                }
            }
        }
    }
}