package com.example.myapplication1.navigation

sealed class Destination(val route: String) {
    object Categories : Destination("categories")


    data class Recipes(val categoryId: Int) : Destination("recipes/{categoryId}") {
        companion object {
            const val CATEGORY_ID_ARG = "categoryId"

            fun createRoute(categoryId: Int) = "recipes/$categoryId"
        }
    }
}