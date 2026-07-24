package com.example.myapplication1.ui.details

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.ContentScale
import com.example.myapplication1.ui.theme.Dimens
import androidx.compose.ui.graphics.painter.rememberVectorPainter

@Composable
fun RecipeDetailHeader(
    title: String,
    imageUrl: String,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    // ТОЛЬКО для ImageVector: берём из Icons
    val favoriteFilledVector = Icons.Filled.Favorite
    val favoriteOutlineVector = Icons.Filled.FavoriteBorder

    // Теперь это корректно: ImageVector -> Painter
    val favoriteFilledPainter = rememberVectorPainter(image = favoriteFilledVector)
    val favoriteOutlinePainter = rememberVectorPainter(image = favoriteOutlineVector)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Header image for $title",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimens.Padding.PaddingMain),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )

            val interactionSource = remember { MutableInteractionSource() }

            IconButton(
                onClick = onFavoriteToggle,
                interactionSource = interactionSource,
                modifier = Modifier.size(40.dp)
            ) {
                Crossfade(targetState = isFavorite, animationSpec = tween(durationMillis = 200)) { favorite ->
                    Icon(
                        painter = if (favorite) favoriteFilledPainter else favoriteOutlinePainter,
                        contentDescription = if (favorite) "Убрать из избранного" else "Добавить в избранное",
                        tint = if (favorite) Color.Red else Color.White
                    )
                }
            }
        }
    }
}