package com.example.myapplication1.navigation

sealed class Destination(val route: String) {
    object Home : Destination("home")
    object Profile : Destination("profile")
    object Settings : Destination("settings")

    data class UserDetail(val userId: String) : Destination("user_detail/$userId") {
        companion object {
            const val USER_ID_ARG = "userId"
            fun createRoute(userId: String) = "user_detail/$userId"
        }
    }

    data class Product(val productId: String) : Destination("product/$productId") {
        companion object {
            const val PRODUCT_ID_ARG = "productId"
            fun createRoute(productId: String) = "product/$productId"
        }
    }
}