package com.example.myapplication1

package com.example.myapplication1

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.runBlocking
import com.example.myapplication1.util.PreferencesKeys

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        migrateFromSharedPreferences(this)
    }

    private fun migrateFromSharedPreferences(context: Context) {
        // Получаем старые SharedPreferences с именем FavoriteRecipes
        val sp = context.getSharedPreferences("FavoriteRecipes", Context.MODE_PRIVATE)

        // Читаем старый набор ID избранных рецептов
        val oldSet = sp.getStringSet("favorite_recipe_ids", null) ?: return

        // Если уже пусто — миграция не нужна
        if (oldSet.isEmpty()) return

        // Записываем в DataStore (это асинхронная операция, но для миграции при старте можно runBlocking)
        runBlocking {
            withContext(kotlinx.coroutines.Dispatchers.IO) {
                context.dataStore.edit { prefs: Preferences ->
                    prefs[PreferencesKeys.FAVORITE_RECIPE_IDS] = oldSet.toSet()
                }
            }
        }

        // Очищаем старые SharedPreferences, чтобы не дублировать данные
        sp.edit().clear().apply()
    }
}