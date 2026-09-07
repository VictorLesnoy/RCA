package com.example.myapplication1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.myapplication1.navigation.AppNavHost
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.ui.theme.MyApplication1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = RecipesRepositoryStub

        setContent {
            MyApplication1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(
                        navController = rememberNavController(),
                        repository = repository,
                    )
                }
            }
        }
    }
}