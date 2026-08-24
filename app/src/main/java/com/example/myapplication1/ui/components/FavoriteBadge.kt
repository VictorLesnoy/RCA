package com.example.myapplication1.ui.components

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication1.util.FavoriteDataStoreManager

@Composable
fun FavoriteBadge(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val manager = remember { FavoriteDataStoreManager(context) }
    val count by manager.getFavoriteCountFlow().collectAsState(initial = 0)

    if (count > 0) {
        Badge(
            content = { Text(text = "$count") },
            modifier = modifier
                .size(24.dp)
                .clip(CircleShape)
        )
    }
}