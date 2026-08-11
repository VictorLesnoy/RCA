package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "recipe_app_prefs")

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
            dataStore.updateData { prefs ->
                val ids = prefs[PreferencesKeys.FAVORITE_RECIPE_IDS]?.toMutableSet() ?: mutableSetOf()
                ids.add(recipeId.toString())
                prefs.toMutablePreferences().apply {
                    this[PreferencesKeys.FAVORITE_RECIPE_IDS] = ids
                }
            }
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        withContext(Dispatchers.IO) {
            dataStore.updateData { prefs ->
                val ids = prefs[PreferencesKeys.FAVORITE_RECIPE_IDS]?.toMutableSet() ?: return@updateData prefs
                ids.remove(recipeId.toString())
                prefs.toMutablePreferences().apply {
                    this[PreferencesKeys.FAVORITE_RECIPE_IDS] = ids
                }
            }
        }
    }
}