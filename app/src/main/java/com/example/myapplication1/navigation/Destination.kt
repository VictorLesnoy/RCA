package com.example.myapplication1.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import androidx.navigation.NavType

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Categories : Destination("categories")
    object Recipes : Destination("recipes")

    data class RecipeDetails(val recipeId: String) : Destination(
        route = "recipe/{recipeId}",
        arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
    )

    companion object {
        fun recipeDetailsRoute(recipeId: String) = "recipe/$recipeId"
    }
}