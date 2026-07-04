package com.example.myapplication1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication1.navigation.AppNavHost
import com.example.myapplication1.ui.theme.MyApplication1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplication1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Передаем текущий intent Activity напрямую
                    AppNavHost(deepLinkIntent = intent)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
