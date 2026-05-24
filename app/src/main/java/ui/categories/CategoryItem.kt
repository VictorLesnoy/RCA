package ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication1.ui.categories.model.CategoryUiModel  // скорректированный путь
import androidx.compose.material3.CardDefaults
import com.example.myapplication1.ui.theme.Dimens
/**
 * Компонент для отображения категории в виде карточки.
 *
 * @param category Данные категории для отображения
 * @param onClick Обработчик нажатия на карточку
 * @param modifier Модификатор для настройки внешнего вида
 */
@Composable
fun CategoryItem(
    category: CategoryUiModel,
    desc: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.Padding.PaddingSmall),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.Shadows.ElevationMedium)
    ) {
        Column(
            modifier = Modifier.padding(Dimens.Padding.PaddingMain),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val imageModel = if (category.imageUrl.isNotEmpty()) category.imageUrl else null
            // Изображение с фиксированным соотношением сторон (~1.2)
            AsyncImage(
                model = imageModel,
                contentDescription = category.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder_image),
                error = painterResource(id = R.drawable.error_image)
            )

            // Название категории — заглавными буквами, жирное, цвет primary
            Text(
                text = category.title.uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3
                )
            }
        }
    }