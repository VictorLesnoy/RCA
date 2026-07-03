package com.example.myapplication1.navigation

import android.net.Uri

const val PARAM_CATEGORY_ID = "category_id"
const val PARAM_RECIPE_ID = "recipe_id"

sealed class Destination(val route: String) {
    object Categories : Destination("categories")

    data class Recipes(val categoryId: Int) : Destination("recipes/{${PARAM_CATEGORY_ID}}") {
        companion object {
            const val CATEGORY_ID_ARG = PARAM_CATEGORY_ID

            fun createRoute(categoryId: Int) = "recipes/$categoryId"
        }
    }

    data class RecipeDetail(val recipeId: Int) : Destination("recipe/{${PARAM_RECIPE_ID}}") {
        companion object {
            const val RECIPE_ID_ARG = PARAM_RECIPE_ID

            fun createRoute(recipeId: Int) = "recipe/$recipeId"

            fun createDeepLink(recipeId: Int) =
                android.net.Uri.parse("recipeapp://recipe/$recipeId")

            fun createHttpShareLink(recipeId: Int) =
                android.net.Uri.parse("https://recipes.androidsprint.ru/recipe/$recipeId")
        }
    }
}