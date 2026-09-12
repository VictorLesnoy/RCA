package com.example.myapplication1.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication1.data.repository.RecipesRepository
import com.example.myapplication1.ui.recipes.RecipeItem
import com.example.myapplication1.ui.recipes.RecipeUiModel
import com.example.myapplication1.ui.theme.Dimens
import com.example.myapplication1.util.FavoriteDataStoreManager
import com.example.myapplication1.navigation.Destination  // путь подставьте свой, где лежит Destination
import com.example.myapplication1.navigation.routeWithId // или сразу используйте Destination.RecipeDetails.routeWithId(...)
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.map

@Composable
fun FavoritesScreen(
    repository: RecipesRepository,
    manager: FavoriteDataStoreManager,
    onNavigate: (String) -> Unit,
) {
    val favorites by remember(repository, manager) {
        manager.getFavoriteIdsFlow().map { ids ->
            ids.mapNotNull { idString ->
                val id = idString.toIntOrNull() ?: return@mapNotNull null
                repository.getRecipeById(id)?.toUiModel()
            }
        }
    }.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Избранное") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (favorites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Пока нет избранных рецептов",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Dimens.Padding.PaddingMain),
                    contentPadding = PaddingValues(vertical = Dimens.Padding.PaddingMain)
                ) {
                    items(
                        items = favorites,
                        key = { it.id }
                    ) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onRecipeClick = {
                                onNavigate(routeWithId(recipe.id))
                            }
                        )
                    }
                }
            }
        }
    }
}