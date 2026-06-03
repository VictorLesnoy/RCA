package com.example.myapplication1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication1.navigation.BottomNavigation
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.theme.RecipesAppTheme
import com.example.myapplication1.ui.favorites.FavoritesScreen

@Composable
fun RecipesApp() {
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedCategoryTitle by remember { mutableStateOf("") }

    RecipesAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { currentScreen = ScreenId.CATEGORIES },
                    onFavoriteClick = { currentScreen = ScreenId.FAVORITES },
                    onRecipesClick = {
                        // Если категория не выбрана, показываем дефолтную
                        if (selectedCategoryId == null) {
                            selectedCategoryId = 1
                            selectedCategoryTitle = "Десерты"
                        }
                        currentScreen = ScreenId.RECIPES
                    }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (currentScreen) {
                        ScreenId.CATEGORIES -> RecipesListScreen(
                            onCategorySelect = { id, title ->
                                selectedCategoryId = id
                                selectedCategoryTitle = title
                                currentScreen = ScreenId.RECIPES
                            }
                        )
                        ScreenId.FAVORITES -> FavoritesScreen()
                        ScreenId.RECIPES -> RecipesScreen(
                            categoryId = selectedCategoryId ?: 0,
                            categoryTitle = selectedCategoryTitle,
                            onRecipeClick = { recipeId ->
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun RecipesListScreen(onCategorySelect: (Int, String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        listOf(
            Pair(1, "Десерты"),
            Pair(2, "Супы"),
            Pair(3, "Основные блюда")
        ).forEach { (id, title) ->
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onCategorySelect(id, title) },
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipesAppTheme {
        RecipesApp()
    }
}