package com.example.myapplication1.utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class FavoritePrefsManager(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_FAVORITES = "favorites_ids"

        fun fromContext(context: android.content.Context): FavoritePrefsManager {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return FavoritePrefsManager(prefs)
        }
    }

    // Возвращает true, если ID есть в списке избранного
    fun isFavorite(recipeId: String): Boolean {
        val favoritesJson = sharedPreferences.getString(KEY_FAVORITES, "[]") ?: "[]"
        // Простой парсинг JSON массива строк (для стаба достаточно)
        return favoritesJson.contains("\"$recipeId\"")
    }

    // Добавляет ID в избранное (без дубликатов)
    fun addToFavorites(recipeId: String) {
        val currentJson = sharedPreferences.getString(KEY_FAVORITES, "[]") ?: "[]"
        val updatedJson = addIdToList(currentJson, recipeId)
        sharedPreferences.edit().putString(KEY_FAVORITES, updatedJson).apply()
    }

    // Удаляет ID из избранного
    fun removeFromFavorites(recipeId: String) {
        val currentJson = sharedPreferences.getString(KEY_FAVORITES, "[]") ?: "[]"
        val updatedJson = removeIdFromList(currentJson, recipeId)
        sharedPreferences.edit().putString(KEY_FAVORITES, updatedJson).apply()
    }

    // Получает все ID избранного (если нужно для другого экрана)
    fun getAllFavorites(): List<String> {
        val json = sharedPreferences.getString(KEY_FAVORITES, "[]") ?: "[]"
        return parseIdsFromJson(json)
    }

    private fun addIdToList(json: String, id: String): String {
        if (isInList(json, id)) return json
        // Очень простой способ добавить ID в JSON массив строк
        // В реальном проекте лучше использовать Gson/Moshi
        val withoutBrackets = json.substring(1, json.length - 1)
        val newList = if (withoutBrackets.isBlank()) "\"$id\"" else "$withoutBrackets,\"$id\""
        return "[$newList]"
    }

    private fun removeIdFromList(json: String, id: String): String {
        val withoutBrackets = json.substring(1, json.length - 1)
        val items = withoutBrackets.split(",").map { it.trim() }
        val filtered = items.filter { it != "\"$id\"" }
        return "[${filtered.joinToString(",")}]"
    }

    private fun isInList(json: String, id: String): Boolean = json.contains("\"$id\"")

    private fun parseIdsFromJson(json: String): List<String> {
        if (json.isBlank() || json == "[]") return emptyList()
        val withoutBrackets = json.substring(1, json.length - 1)
        return withoutBrackets.split(",").map { it.trim().removeSurrounding("\"") }
    }
}