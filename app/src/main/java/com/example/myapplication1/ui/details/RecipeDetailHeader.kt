package com.example.myapplication1.ui.details

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.myapplication1.R

@Composable
fun RecipeDetailHeader(
    title: String,
    imageUrl: String,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Header image for $title",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.foundation.layout.ContentScale.Crop
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
                // Анимированный переход между иконками
                Crossfade(targetState = isFavorite, animationSpec = tween(durationMillis = 200)) { favorite ->
                    Icon(
                        painter = if (favorite)
                            painterResource(id = R.drawable.ic_favorite_filled)
                        else
                            painterResource(id = R.drawable.ic_favorite_outline),
                        contentDescription = if (favorite) "Убрать из избранного" else "Добавить в избранное",
                        tint = if (favorite) Color.Red else Color.White
                    )
                }
            }
        }
    }
}