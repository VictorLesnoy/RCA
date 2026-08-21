package com.example.myapplication1.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication1.util.Destination
import com.example.myapplication1.ui.components.FavoriteBadge
import com.google.accompanist.icons.Icons

@Composable
fun BottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
) {
    val context = LocalContext.current

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Destination.Recipes.route,
            onClick = { onNavigate(Destination.Recipes.route) },
            icon = { Icon(Icons.Filled.List, contentDescription = null) },
            label = { Text("Рецепты") }
        )

        NavigationBarItem(
            selected = currentRoute == Destination.Favorites.route,
            onClick = { onNavigate(Destination.Favorites.route) },
            icon = {
                Box(modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    FavoriteBadge(
                        context = context,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 8.dp, y = -8.dp)
                            .size(16.dp) // чуть меньше, чтобы красиво смотрелся
                    )
                }
            },
            label = { Text("Избранное") }
        )
    }
}