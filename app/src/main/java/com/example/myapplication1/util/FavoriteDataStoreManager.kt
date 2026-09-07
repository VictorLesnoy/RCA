package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferenceKeyOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.myapplication1.util.PreferencesKeys

class FavoriteDataStoreManager(private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    fun getFavoriteIdsFlow(): Flow<Set<String>> =
        dataStore.data
            .catch { exception ->
                emit(emptyPreferences())
            }
            .map { prefs ->
                @Suppress("UNCHECKED_CAST")
                (prefs[PreferencesKeys.FAVORITE_RECIPE_IDS] as? Set<String>) ?: emptySet()
            }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> =
        getFavoriteIdsFlow().map { ids ->
            ids.contains(recipeId.toString())
        }

    fun getFavoriteCountFlow(): Flow<Int> =
        getFavoriteIdsFlow().map { it.size }

    suspend fun addFavorite(recipeId: Int) {
        val idString = recipeId.toString()
        dataStore.updateData { currentPreferences ->
            val ids = (currentPreferences[PreferencesKeys.FAVORITE_RECIPE_IDS] as? Set<String>)?.toMutableSet()
                ?: mutableSetOf()

            ids.add(idString)

            currentPreferences.toMutablePreferences().apply {
                this[PreferencesKeys.FAVORITE_RECIPE_IDS] = ids
            }
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        val idString = recipeId.toString()
        dataStore.updateData { currentPreferences ->
            @Suppress("UNCHECKED_CAST")
            val currentIds = currentPreferences[PreferencesKeys.FAVORITE_RECIPE_IDS] as? Set<String>
            if (currentIds == null) return@updateData currentPreferences

            val ids = currentIds.toMutableSet()
            ids.remove(idString)

            currentPreferences.toMutablePreferences().apply {
                if (ids.isEmpty()) {
                    remove(PreferencesKeys.FAVORITE_RECIPE_IDS)
                } else {
                    this[PreferencesKeys.FAVORITE_RECIPE_IDS] = ids
                }
            }
        }
    }
}