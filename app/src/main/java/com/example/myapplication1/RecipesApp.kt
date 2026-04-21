package com.example.myapplication1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication1.navigation.BottomNavigation
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.theme.RecipesAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// Перечисление экранов приложения
enum class ScreenId {
    CATEGORIES,
    FAVORITES,
    RECIPES
}

@Composable
fun RecipesApp() {
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }

    RecipesAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { currentScreen = ScreenId.CATEGORIES },
                    onFavoriteClick = { currentScreen = ScreenId.FAVORITES },
                    onRecipesClick = { currentScreen = ScreenId.RECIPES }  // Новый обработчик
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (currentScreen) {
                        ScreenId.CATEGORIES -> RecipesListScreen()
                        ScreenId.FAVORITES -> FavoritesitesScreen()
                        ScreenId.RECIPES -> RecipesScreen()  // Новый экран добавлен
                    }
                }
            }
        )
    }
}

@Composable
fun RecipesListScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "ЭКРАН КАТЕГОРИЙ",
            fontSize = 20.sp
        )
    }
}

@Composable
fun FavoritesitesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "ЭКРАН ИЗБРАННОГО",
            fontSize = 20.sp
        )
    }
}

@Composable
fun RecipesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "ЭКРАН РЕЦЕПТОВ",
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipesAppTheme {
        RecipesApp()
    }
}