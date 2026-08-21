package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import com.example.myapplication1.util.PreferencesKeys

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
                val currentIds = prefs[PreferencesKeys.FAVORITE_RECIPE_IDS]
                if (currentIds == null) return@updateData prefs

                val ids = currentIds.toMutableSet()
                ids.remove(recipeId.toString())

                prefs.toMutablePreferences().apply {
                    if (ids.isEmpty()) {
                        remove(PreferencesKeys.FAVORITE_RECIPE_IDS)
                    } else {
                        this[PreferencesKeys.FAVORITE_RECIPE_IDS] = ids
                    }
                }
            }
        }
    }
}