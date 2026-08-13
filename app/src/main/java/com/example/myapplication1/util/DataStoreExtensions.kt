package com.example.myapplication1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "recipe_app_prefs",
    produceMigrations = { context ->
        listOf(
            androidx.datastore.preferences.core.produceMigration {
                SharedPreferencesToDataStoreMigration(context).migrate()
            }
        )
    }
)