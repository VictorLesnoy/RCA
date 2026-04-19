package ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun ScreenHeader(
    imagePainter: Painter,
    contentDescription: String,
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(Dimens.HeaderHeight)
    ) {
        // Фон — изображение, заполняющее весь Box
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Surface с заголовком в нижнем левом углу
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = Dimens.Padding.PaddingMain,
                    bottom = Dimens.Padding.PaddingMediumLarge
                ),
            tonalElevation = Dimens.Shadows.ElevationMedium
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(
                    horizontal = Dimens.Padding.PaddingMain,
                    vertical = Dimens.Padding.PaddingMedium
                ),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}