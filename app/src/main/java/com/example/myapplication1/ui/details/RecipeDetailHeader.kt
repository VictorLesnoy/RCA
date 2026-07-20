package com.example.myapplication1.ui.details

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.ContentScale
import com.example.myapplication1.R
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun RecipeDetailHeader(
    title: String,
    imageUrl: String,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    val favoriteFilledPainter = androidx.compose.ui.graphics.painter.rememberVectorPainter(
        image = vectorResource(id = R.drawable.ic_favorite_filled)
    )

    val favoriteOutlinePainter = androidx.compose.ui.graphics.painter.rememberVectorPainter(
        image = vectorResource(id = R.drawable.ic_favorite_outline)
    )

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
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

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
                fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold,
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