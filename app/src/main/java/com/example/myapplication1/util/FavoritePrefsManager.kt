package com.example.myapplication1.util

import android.content.Context
import android.content.SharedPreferences

class FavoritePrefsManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "favorites_prefs"
        private const val KEY_FAVORITE_IDS = "favorite_recipe_ids"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean {
        val favorites = getFavoritesSet()
        return favorites.contains(recipeId.toString())
    }

    fun addToFavorites(recipeId: Int) {
        val set = getFavoritesSet().toMutableSet()
        set.add(recipeId.toString())
        saveFavoritesSet(set)
    }

    fun removeFromFavorites(recipeId: Int) {
        val set = getFavoritesSet().toMutableSet()
        set.remove(recipeId.toString())
        saveFavoritesSet(set)
    }

    fun getAllFavorites(): Set<String> = getFavoritesSet()

    private fun getFavoritesSet(): Set<String> {
        // getStringSet может вернуть null, поэтому обрабатываем безопасно
        return prefs.getStringSet(KEY_FAVORITE_IDS, emptySet())?.toSet() ?: emptySet()
    }

    private fun saveFavoritesSet(set: Set<String>) {
        prefs.edit {
            putStringSet(KEY_FAVORITE_IDS, set)
            apply() // асинхронно; если критично сразу — можно заменить на commit()
        }
    }
}