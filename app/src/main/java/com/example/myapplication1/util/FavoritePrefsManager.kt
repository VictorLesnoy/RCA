package com.example.myapplication1.utils

import android.content.Context
import android.content.SharedPreferences

class FavoritePrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_FAVORITES = "favorite_recipe_ids"

        fun fromContext(context: Context): FavoritePrefsManager {
            return FavoritePrefsManager(context)
        }
    }

    // Возвращает true, если ID есть в списке избранного
    fun isFavorite(recipeId: Int): Boolean {
        val favoritesSet = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet())
        return favoritesSet?.contains(recipeId.toString()) == true
    }

    // Добавляет ID в избранное (без дубликатов)
    fun addToFavorites(recipeId: Int) {
        val currentSet = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet()).toMutableSet()
        currentSet.add(recipeId.toString())

        sharedPreferences.edit().putStringSet(KEY_FAVORITES, currentSet).apply()
    }

    // Удаляет ID из избранного
    fun removeFromFavorites(recipeId: Int) {
        val currentSet = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet()).toMutableSet()
        currentSet.remove(recipeId.toString())

        sharedPreferences.edit().putStringSet(KEY_FAVORITES, currentSet).apply()
    }

    // Получает все ID избранного (если нужно для другого экрана)
    fun getAllFavorites(): List<Int> {
        val favoritesSet = sharedPreferences.getStringSet(KEY_FAVORITES, emptySet())
        return favoritesSet
            ?.mapNotNull { it.toIntOrNull() }
            .orEmpty()
    }
}