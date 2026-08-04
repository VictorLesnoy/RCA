package com.example.myapplication1.data

import android.content.Context
import android.content.SharedPreferences

class FavoritePrefsManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "favorites_prefs"
        private const val KEY_FAVORITE_IDS = "favorite_ids"
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isFavorite(recipeId: Int): Boolean {
        val favoriteIds = prefs.getStringSet(KEY_FAVORITE_IDS, emptySet()) ?: emptySet()
        return favoriteIds.contains(recipeId.toString())
    }

    fun toggleFavorite(recipeId: Int) {
        prefs.edit { editor ->
            val favoriteIds = editor.getStringSet(KEY_FAVORITE_IDS, emptySet())?.toMutableSet() ?: mutableSetOf()
            val idString = recipeId.toString()

            if (favoriteIds.contains(idString)) {
                favoriteIds.remove(idString)
            } else {
                favoriteIds.add(idString)
            }

            editor.putStringSet(KEY_FAVORITE_IDS, favoriteIds)
        }
    }
}

// Extension для удобного использования editor.edit { }
private inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    action(editor)
    editor.apply() // apply() вместо commit() — асинхронно, без блокировки UI
}