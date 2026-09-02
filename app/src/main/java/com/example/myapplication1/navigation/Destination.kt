package com.example.myapplication1.navigation

import androidx.navigation.NavType

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes") {
    }

    data class RecipeDetails(val recipeId: Int) : Destination("recipe/{recipeId}") {
        companion object {
            fun routeWithId(recipeId: Int): String = "recipe/$recipeId"
        }
    }
}