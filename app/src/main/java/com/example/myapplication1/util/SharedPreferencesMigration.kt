package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.myapplication1.util.PreferencesKeys

class SharedPreferencesMigration(private val context: Context) {

    suspend fun migrate() {
        val sp = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
        val oldSet = sp.getStringSet("favorite_recipe_ids", null) ?: return
        if (oldSet.isEmpty()) return

        context.dataStore.updateData { currentPrefs ->
            currentPrefs.toMutablePreferences().apply {
                this[PreferencesKeys.FAVORITE_RECIPE_IDS] = oldSet.toSet()
            }
        }

        sp.edit().clear().apply()
    }
}