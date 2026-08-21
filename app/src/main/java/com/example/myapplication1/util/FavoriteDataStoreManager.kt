package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.myapplication1.util.PreferencesKeys

class FavoriteDataStoreManager(private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    fun getFavoriteIdsFlow(): Flow<Set<String>> =
        dataStore.data.map { prefs ->
            prefs[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> =
        getFavoriteIdsFlow().map { ids ->
            ids.contains(recipeId.toString())
        }

    fun getFavoriteCountFlow(): Flow<Int> =
        getFavoriteIdsFlow().map { it.size }

    suspend fun addFavorite(recipeId: Int) {
        dataStore.updateData { prefs ->
            val ids = prefs[PreferencesKeys.FAVORITE_RECIPE_IDS]?.toMutableSet() ?: mutableSetOf()
            ids.add(recipeId.toString())
            prefs.toMutablePreferences().apply {
                this[PreferencesKeys.FAVORITE_RECIPE_IDS] = ids
            }
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
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