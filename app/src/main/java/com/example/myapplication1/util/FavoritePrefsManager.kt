package com.example.myapplication1.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class FavoritePrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_FAVORITES = "favorite_recipe_ids"

        fun fromContext(context: Context): FavoritePrefsManager {
            return FavoritePrefsManager(context)
        }
    }

    fun isFavorite(recipeId: Int): Boolean {
        val favoritesSet = sharedPreferences.getStringSet(KEY_FAVORITES, null) ?: emptySet()
        return favoritesSet.contains(recipeId.toString())
    }

    fun addToFavorites(recipeId: Int) {
        val currentSet = (sharedPreferences.getStringSet(KEY_FAVORITES, null) ?: emptySet()).toMutableSet()
        currentSet.add(recipeId.toString())

        sharedPreferences.edit { putStringSet(KEY_FAVORITES, currentSet) }
    }

    fun removeFromFavorites(recipeId: Int) {
        val currentSet = (sharedPreferences.getStringSet(KEY_FAVORITES, null) ?: emptySet()).toMutableSet()
        currentSet.remove(recipeId.toString())

        sharedPreferences.edit { putStringSet(KEY_FAVORITES, currentSet) }
    }

    fun getAllFavorites(): List<Int> {
        val favoritesSet = sharedPreferences.getStringSet(KEY_FAVORITES, null) ?: emptySet()
        return favoritesSet
            .mapNotNull { it.toIntOrNull() }
            .toList()
    }
}