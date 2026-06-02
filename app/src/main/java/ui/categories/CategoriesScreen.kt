package ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication1.R
import com.example.myapplication1.ui.components.ScreenHeader
import com.example.myapplication1.data.repository.RecipesRepositoryStub
import com.example.myapplication1.ui.theme.Dimens
import com.example.myapplication1.ui.categories.model.CategoryUiModel
import com.example.myapplication1.ui.categories.model.toUiModel

/**
 * Экран отображения категорий рецептов.
 *
 * @param modifier Модификатор для настройки внешнего вида (например, padding из Scaffold)
 * @param onCategoryClick Обработчик нажатия на категорию, принимает ID категории
 */
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int) -> Unit
) {
    // Состояние для хранения списка категорий и статуса загрузки
    val categories = remember { mutableStateOf<List<CategoryUiModel>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    // Загрузка данных при создании экрана
    LaunchedEffect(Unit) {
        try {
            val categoryDtos = RecipesRepositoryStub.getCategories()
            categories.value = categoryDtos.map { it.toUiModel() }
        } catch (e: Exception) {
            error.value = "Ошибка загрузки категорий: ${e.message}"
        } finally {
            isLoading.value = false
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Заголовок экрана сверху (вне сетки)
        ScreenHeader(
            imagePainter = painterResource(id = R.drawable.categories_header),
            contentDescription = "Фон экрана категорий",
            title = "Категории"
        )

        if (isLoading.value) {
            // Индикатор загрузки
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Загрузка категорий...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = Dimens.Padding.PaddingMedium)
                )
            }
        } else if (error.value != null) {
            // Сообщение об ошибке
            Text(
                text = error.value ?: "Неизвестная ошибка",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        } else if (categories.value.isEmpty()) {
            // Сообщение, если нет категорий
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Категории не найдены",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            // Сетка категорий с двумя колонками
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(Dimens.Padding.PaddingMedium),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Padding.PaddingMedium),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    top = Dimens.Padding.PaddingSmall,
                    bottom = Dimens.Padding.PaddingLarge
                )
            ) {
                items(categories.value, key = { it.id })
                { category ->
                    CategoryItem(
                        category = category,
                        onClick = { onCategoryClick(category.id) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}