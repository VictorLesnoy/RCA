package ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.example.myapplication1.R
import ui.components.ScreenHeader
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun CategoriesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = Dimens.Padding.PaddingMain,
                end = Dimens.Padding.PaddingMain,
                bottom = Dimens.Padding.PaddingLarge
            )
    ) {
        // Заголовок экрана с использованием существующего компонента
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.categories_header),
            contentDescription = "Фон экрана категорий",
            title = "Категории"
        )

        // Заглушка для списка категорий
        CategoryListPlaceholder()
    }
}

@Composable
private fun CategoryListPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding.PaddingMedium)
    ) {
        // Создаём 5 заглушек для категорий
        repeat(5) { index ->
            CategoryItemPlaceholder(position = index + 1)
        }
    }
}

@Composable
private fun CategoryItemPlaceholder(position: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = Dimens.Shadows.ElevationMedium
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Padding.PaddingMain),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Категория №$position",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}