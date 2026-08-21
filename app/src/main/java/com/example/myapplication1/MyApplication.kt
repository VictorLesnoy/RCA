package com.example.myapplication1

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.myapplication1.util.SharedPreferencesMigration

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    private fun migrateFromSharedPreferences(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val migration = SharedPreferencesMigration(context)
            migration.migrate()
        }
    }
}