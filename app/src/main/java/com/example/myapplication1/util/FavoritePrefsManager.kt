package com.example.myapplication1.util

import android.content.Context
import android.content.SharedPreferences

class FavoritePrefsManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "favorites_prefs"
        private const val KEY_FAVORITE_RECIPE_IDS = "favorite_recipe_ids"
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Возвращает множество ID избранных рецептов (как строки).
     */
    fun getAllFavorites(): Set<String> {
        return prefs.getStringSet(KEY_FAVORITE_RECIPE_IDS, emptySet()) ?: emptySet()
    }

    /**
     * Проверяет, добавлен ли рецепт в избранное.
     */
    fun isFavorite(recipeId: Int): Boolean {
        val favoriteIds = getAllFavorites()
        return favoriteIds.contains(recipeId.toString())
    }

    /**
     * Добавляет рецепт в избранное (если ещё не добавлен).
     */
    fun addToFavorites(recipeId: Int) {
        prefs.edit { editor ->
            val favoriteIds = editor.getStringSet(KEY_FAVORITE_RECIPE_IDS, emptySet())?.toMutableSet()
                ?: mutableSetOf()
            favoriteIds.add(recipeId.toString())
            editor.putStringSet(KEY_FAVORITE_RECIPE_IDS, favoriteIds)
        }
    }

    /**
     * Удаляет рецепт из избранного (если он там есть).
     */
    fun removeFromFavorites(recipeId: Int) {
        prefs.edit { editor ->
            val favoriteIds = editor.getStringSet(KEY_FAVORITE_RECIPE_IDS, emptySet())?.toMutableSet()
                ?: mutableSetOf()
            favoriteIds.remove(recipeId.toString())
            editor.putStringSet(KEY_FAVORITE_RECIPE_IDS, favoriteIds)
        }
    }

    /**
     * Переключает статус избранного (для совместимости, если где-то уже используется).
     * Реализуется через add/remove.
     */
    fun toggleFavorite(recipeId: Int) {
        if (isFavorite(recipeId)) {
            removeFromFavorites(recipeId)
        } else {
            addToFavorites(recipeId)
        }
    }
}

private inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    action(editor)
    editor.apply() // apply() — асинхронно, не блокирует UI
}