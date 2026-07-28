package com.example.myapplication1.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication1.ui.recipes.RecipeUiModel

class FavoritePrefsManager(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean =
        prefs.getBoolean(recipeId.toString(), false)

    fun addToFavorites(recipe: RecipeUiModel) {
        prefs.edit().putBoolean(recipe.id.toString(), true).apply()
    }

    fun removeFromFavorites(recipeId: Int) {
        prefs.edit().remove(recipeId.toString()).apply()
    }

    fun getAllFavorites(): List<Int> =
        prefs.all.filter { it.value == true }.mapNotNull { it.key.toIntOrNull() }
}

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