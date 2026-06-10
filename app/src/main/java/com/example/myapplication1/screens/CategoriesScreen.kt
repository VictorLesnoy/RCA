package com.example.myapplication1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CategoriesScreen(
    onCategoryClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Экран категорий")

        Button(onClick = { onCategoryClick(1) }) {
            Text("Рецепты категории 1")
        }

        Button(onClick = { onCategoryClick(2) }) {
            Text("Рецепты категории 2")
        }

        Button(onClick = { onCategoryClick(3) }) {
            Text("Рецепты категории 3")
        }
    }
}