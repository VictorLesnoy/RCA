package com.example.myapplication1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Главный экран")

        Button(onClick = {
            navController.navigate(Destinations.Profile.route)
        }) {
            Text("Перейти в профиль")
        }

        Button(onClick = {
            navController.navigate(Destinations.UserDetail.createRoute("123"))
        }) {
            Text("Детали пользователя 123")
        }
    }
}