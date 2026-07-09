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

class MainActivity : ComponentActivity() {

    private var latestIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        latestIntent = intent

        setContent {
            MyApplication1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Храним сам MutableState, а не значение
                    val deepLinkState = remember { mutableStateOf(latestIntent) }

                    // Синхронизируем значение внутри состояния с полем Activity
                    deepLinkState.value = latestIntent

                    // Передаём текущее значение в AppNavHost
                    AppNavHost(deepLinkIntent = deepLinkState.value)
                }
            }
        }
    }

    override fun onNewIntent(newIntent: Intent) {
        super.onNewIntent(newIntent)
        setIntent(newIntent)

        latestIntent = newIntent
        // При следующей рекомпозиции deepLinkState.value подтянет новое значение
    }
}

