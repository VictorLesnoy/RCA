package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

class FavoriteDataStoreManager(private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun isFavorite(recipeId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val ids = dataStore.data.first()[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            ids.contains(recipeId.toString())
        }
    }

    suspend fun addFavorite(recipeId: Int) {
        withContext(Dispatchers.IO) {
            dataStore.edit { prefs ->
                val ids = prefs[PreferencesKeys.FAVORITE_RECIPE_IDS]?.toMutableSet() ?: mutableSetOf()
                ids.add(recipeId.toString())
                prefs[PreferencesKeys.FAVORITE_RECIPE_IDS] = ids
            }
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        withContext(Dispatchers.IO) {
            dataStore.edit { prefs ->
                val ids = prefs[PreferencesKeys.FAVORITE_RECIPE_IDS]?.toMutableSet() ?: return@edit
                ids.remove(recipeId.toString())
                prefs[PreferencesKeys.FAVORITE_RECIPE_IDS] = ids
            }
        }
    }
}