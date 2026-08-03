package com.example.myapplication1.navigation

import androidx.navigation.NavType

sealed class Destination(val route: String) {
    object Categories : Destination("categories")

    // Шаблонный route для списка рецептов
    object Recipes : Destination("recipes/{categoryId?}") {
        val categoryIdArg = NavType.IntType(isNullable = true)
    }

    // Детали рецепта с ID
    data class RecipeDetails(val recipeId: Int) : Destination("recipe/{recipeId}") {
        companion object {
            fun routeWithId(recipeId: Int): String = "recipe/$recipeId"
        }
    }
}