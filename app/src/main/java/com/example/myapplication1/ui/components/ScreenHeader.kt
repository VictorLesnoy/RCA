package com.example.myapplication1.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.ContentScale
import com.example.myapplication1.R
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun ScreenHeader(
    title: String,
    imageUrl: String,
    showFavoriteButton: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteToggle: (() -> Unit)? = null
) {
    val favoriteFilledPainter = painterResource(id = R.drawable.ic_favorite_filled)
    val favoriteOutlinePainter = painterResource(id = R.drawable.ic_favorite_outline)

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
                fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            if (showFavoriteButton && onFavoriteToggle != null) {
                IconButton(
                    onClick = onFavoriteToggle,
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
}

@Preview(showBackground = true)
@Composable
fun ScreenHeaderPreview() {
    ScreenHeader(title = "Рецепт дня", imageUrl = "https://via.placeholder.com/400x300")
}

@Preview(showBackground = true)
@Composable
fun ScreenHeaderWithFavoritePreview() {
    ScreenHeader(
        title = "Борщ",
        imageUrl = "https://via.placeholder.com/400x300",
        showFavoriteButton = true,
        isFavorite = true,
        onFavoriteToggle = {}
    )
}