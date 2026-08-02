package com.example.myapplication1.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import androidx.navigation.NavType

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object Categories : Destination("categories")

    data class Recipes(val categoryId: Int? = null) : Destination(
        route = if (categoryId == null) "recipes" else "recipes/${categoryId}",
        arguments = if (categoryId != null) listOf(navArgument("categoryId") { type = NavType.IntType }) else emptyList()
    )

    object RecipeDetails : Destination(
        route = "recipe/{recipeId}",
        arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
    )

    companion object {
        fun recipeDetailsRoute(recipeId: Int) = "${RecipeDetails.route.replace("{recipeId}", "$recipeId")}"

        fun recipesRoute(categoryId: Int? = null): String =
            if (categoryId == null) Recipes(null).route else Recipes(categoryId).route
    }
}