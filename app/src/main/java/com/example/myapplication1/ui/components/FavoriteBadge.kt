package com.example.myapplication1.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication1.util.FavoriteDataStoreManager
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.collectAsState

@Composable
fun FavoriteBadge(
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier
) {
    val manager = remember { FavoriteDataStoreManager(context) }
    val count by manager.getFavoriteCountFlow().collectAsState(initial = 0)

    if (count > 0) {
        Badge(
            content = { androidx.compose.material3.Text(text = "$count") },
            modifier = modifier
                .size(24.dp)
                .clip(CircleShape)
        )
    }
}