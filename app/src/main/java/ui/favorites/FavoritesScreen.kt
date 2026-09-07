package com.example.myapplication1.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.ui.recipes.RecipeCard
import com.example.myapplication1.ui.theme.Dimens
import com.example.myapplication1.util.FavoriteDataStoreManager
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun FavoritesScreen(
    repository: RecipesRepositoryStub,
    manager: FavoriteDataStoreManager,
    onNavigate: (String) -> Unit,
) {
    val favoriteIds by manager.getFavoriteIdsFlow().filterNotNull().collectAsState(initial = emptyList())

    var favorites by remember { mutableStateOf<List<RecipeCard.RecipeUiModel>?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(favoriteIds) {
        isLoading = true
        try {
            val recipes = favoriteIds.mapNotNull { id ->
                repository.getRecipeById(id)
            }.map { it.toUiModel() }
            favorites = recipes
        } catch (e: Exception) {
            favorites = emptyList()
        } finally {
            isLoading = false
        }
    }

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
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (favorites.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.FavoriteBorder,
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
                    items(favorites!!) { recipe ->
                        RecipeCard(
                            recipe = recipe,
                            onCardClick = {
                                val route = "${com.example.myapplication1.util.Destination.RecipeDetails.route}/${recipe.id}"
                                onNavigate(route)
                            },
                            isFavorite = manager.isFavoriteFlow(recipe.id).collectAsState(initial = false).value
                        )
                    }
                }
            }
        }
    }
}