package com.example.myapplication1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.ui.components.BottomNav
import com.example.myapplication1.ui.details.RecipeDetailsScreen
import com.example.myapplication1.ui.recipes.RecipesScreen
import com.example.myapplication1.ui.favorites.FavoritesScreen
import com.example.myapplication1.util.Destination
import androidx.compose.material3.Scaffold

@Composable
fun AppNavHost(
    navController: NavHostController,
    repository: RecipesRepositoryStub,
) {
    val currentEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry.value?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNav(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Recipes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destination.Recipes.route) {
                RecipesScreen(
                    repository = repository,
                    onRecipeClick = { recipeId ->
                        navController.navigate("${Destination.RecipeDetails.route}/$recipeId")
                    },
                    onBackClick = null
                )
            }

            composable(Destination.Favorites.route) {
                FavoritesScreen()
            }

            composable("${Destination.RecipeDetails.route}/{recipeId}") { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                RecipeDetailsScreen(
                    recipeId = recipeId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}