package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SharedPreferencesToDataStoreMigration(
    private val context: Context
) {
    suspend fun migrate(): Preferences {
        val sp = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
        val oldSet = sp.getStringSet("favorite_recipe_ids", null) ?: return@migrate Preferences.EMPTY

        if (oldSet.isEmpty()) {
            return Preferences.EMPTY
        }

        val newPrefs = Preferences.mutablePreferencesOf()
        newPrefs[PreferencesKeys.FAVORITE_RECIPE_IDS] = oldSet.toSet()

        sp.edit().clear().apply()

        return newPrefs
    }
}