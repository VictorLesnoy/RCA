package com.example.myapplication1

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.myapplication1.navigation.AppNavHost
import com.example.myapplication1.ui.components.FavoriteBadge
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.util.Destination
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = RecipesRepositoryStub

        setContent {
            MyApplication1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(
                        navController = rememberNavController(),
                        repository = repository,
                    )
                }
            }
        }
    }

    companion object {
        @Composable
        fun BottomNav(
            currentRoute: String?,
            onNavigate: (String) -> Unit,
        ) {
            val context = LocalContext.current

            NavigationBar {
                NavigationItem(
                    selected = currentRoute == Destination.Recipes.route,
                    onClick = { onNavigate(Destination.Recipes.route) },
                    icon = { Icon(Icons.Filled.List, contentDescription = null) },
                    label = { Text("Рецепты") }
                )

                NavigationItem(
                    selected = currentRoute == Destination.Favorites.route,
                    onClick = { onNavigate(Destination.Favorites.route) },
                    icon = {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                        FavoriteBadge(
                            context = context,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 8.dp, y = -8.dp)
                        )
                    },
                    label = { Text("Избранное") }
                )

            }
        }
    }
}