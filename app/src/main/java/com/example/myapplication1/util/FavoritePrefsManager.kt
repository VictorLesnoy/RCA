package com.example.myapplication1.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication1.ui.recipes.RecipeUiModel

class FavoritePrefsManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "favorites_prefs"
        const val KEY_FAVORITE_IDS = "favorite_recipe_ids"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Проверяет, добавлен ли рецепт с recipeId в избранное.
     */
    fun isFavorite(recipeId: Int): Boolean {
        val favoritesSet = getFavoritesSet()
        return favoritesSet.contains(recipeId.toString())
    }

    /**
     * Добавляет рецепт в избранное, сохраняя его ID в Set.
     */
    fun addToFavorites(recipe: RecipeUiModel) {
        val currentSet = getFavoritesSet().toMutableSet()
        currentSet.add(recipe.id.toString())
        saveFavoritesSet(currentSet)
    }

    /**
     * Удаляет рецепт из избранного по ID.
     */
    fun removeFromFavorites(recipeId: Int) {
        val currentSet = getFavoritesSet().toMutableSet()
        currentSet.remove(recipeId.toString())
        saveFavoritesSet(currentSet)
    }

    /**
     * Возвращает список ID всех избранных рецептов.
     */
    fun getAllFavorites(): List<Int> =
        getFavoritesSet()
            .mapNotNull { it.toIntOrNull() }
            .toList()

    /**
     * Получает текущий Set ID избранных рецептов (безопасно обрабатывает null).
     */
    private fun getFavoritesSet(): Set<String> {
        return prefs.getStringSet(KEY_FAVORITE_IDS, emptySet())?.toSet() ?: emptySet()
    }

    /**
     * Сохраняет обновлённый Set ID избранных рецептов.
     */
    private fun saveFavoritesSet(set: Set<String>) {
        prefs.edit {
            putStringSet(KEY_FAVORITE_IDS, set)
            apply()
        }
    }
}