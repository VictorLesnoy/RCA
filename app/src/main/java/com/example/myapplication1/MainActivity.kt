package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import com.example.myapplication1.navigation.AppNavHost
import com.example.myapplication1.ui.theme.MyApplication1Theme

class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Обработка при первом запуске
        handleDeepLink(intent)

        setContent {
            MyApplication1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Передаем Intent в AppNavHost
                    AppNavHost(deepLinkIntent = deepLinkIntent)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Обработка, если приложение уже было запущено
        handleDeepLink(intent)
    }

    private fun handleDeepLink(incomingIntent: Intent?) {
        // Проверяем наличие data (URI ссылки)
        if (incomingIntent?.data != null) {
            deepLinkIntent = incomingIntent
        }
    }
}