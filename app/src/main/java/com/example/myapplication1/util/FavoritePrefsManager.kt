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

    /**
     * Проверяет, находится ли рецепт в избранном.
     * ID рецепта хранится как String в Set.
     */
    fun isFavorite(recipeId: Int): Boolean {
        val favorites = getFavoritesSet()
        return favorites.contains(recipeId.toString())
    }

    /**
     * Добавляет рецепт в избранное.
     */
    fun addToFavorites(recipeId: Int) {
        val set = getFavoritesSet().toMutableSet()
        set.add(recipeId.toString())
        saveFavoritesSet(set)
    }

    /**
     * Удаляет рецепт из избранного.
     */
    fun removeFromFavorites(recipeId: Int) {
        val set = getFavoritesSet().toMutableSet()
        set.remove(recipeId.toString())
        saveFavoritesSet(set)
    }

    /**
     * Возвращает Set<String> всех ID избранных рецептов.
     */
    fun getAllFavorites(): Set<String> = getFavoritesSet()

    // --- Внутренние методы для работы с SharedPreferences ---

    private fun getFavoritesSet(): Set<String> {
        // putStringSet/getStringSet могут возвращать null в редких случаях — страхуемся
        return prefs.getStringSet(KEY_FAVORITE_IDS, emptySet())?.toSet() ?: emptySet()
    }

    private fun saveFavoritesSet(set: Set<String>) {
        prefs.edit { putStringSet(KEY_FAVORITE_IDS, set) }
    }
}