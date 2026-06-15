package com.example.myapplication1.navigation

sealed class Destination(val route: String) {
    object Categories : Destination("categories")

    data class Recipes(val categoryId: Int) : Destination("recipes/{categoryId}") {
        companion object {
            const val CATEGORY_ID_ARG = "categoryId"

            fun createRoute(categoryId: Int) = "recipes/$categoryId"
        }
    }

    data class RecipeDetail(val recipeId: Int) : Destination("recipe/{recipeId}") {
        companion object {
            const val RECIPE_ID_ARG = "recipeId"
            const val KEY_RECIPE_OBJECT = "recipe_object"

            fun createRoute(recipeId: Int) = "recipe/$recipeId"
        }
    }
}