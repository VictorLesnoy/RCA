package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.myapplication1.navigation.AppNavHost
import com.example.myapplication1.ui.theme.MyApplication1Theme
import java.lang.reflect.Modifier
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val favoritePrefs = FavoritePrefsManager.fromContext(this)
        val repository = RecipesRepositoryStub()

        setContent {
            MyApplication1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(
                        navController = rememberNavController(),
                        repository = repository,
                        favoritePrefs = favoritePrefs
                    )
                }
            }
        }
    }
}