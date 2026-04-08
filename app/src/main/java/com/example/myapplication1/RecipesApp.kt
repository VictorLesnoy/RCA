package com.example.myapplication1

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication1.ui.theme.RecipesAppTheme

@Composable
fun RecipesApp(
    content: @Composable () -> Unit
) {
    RecipesAppTheme {
        Scaffold { paddingValues ->
            Text(
                text = "Recipes App",
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}