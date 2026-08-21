package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.myapplication1.util.PreferencesKeys

class SharedPreferencesMigration(private val context: Context) {

    suspend fun migrate() = withContext(Dispatchers.IO) {
        val sp = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
        val oldSet = sp.getStringSet("favorite_recipe_ids", null) ?: return@withContext
        if (oldSet.isEmpty()) return@withContext

        context.dataStore.updateData { currentPrefs ->
            currentPrefs.toMutablePreferences().apply {
                this[PreferencesKeys.FAVORITE_RECIPE_IDS] = oldSet.toSet()
            }
        }

        sp.edit().clear().apply()
    }
}