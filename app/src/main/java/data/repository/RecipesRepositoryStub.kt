package data.repository

import data.model.CategoryDto
import data.model.IngredientDto
import data.model.RecipeDto
import com.example.myapplication1.ui.categories.model.toUiModel
import com.example.myapplication1.ui.categories.model.CategoryUiModel

/**
 * Заглушка репозитория рецептов для тестирования UI без реального API.
 * Содержит тестовые данные категорий и рецептов бургеров.
 */
class RecipesRepositoryStub {

    // Список категорий блюд
    private val categoryList = listOf(
        CategoryDto(
            0,
            "Бургеры",
            "Рецепты всех популярных видов бургеров",
            "burger.png"
        ),
        CategoryDto(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "dessert.png"
        ),
        CategoryDto(
            2,
            "Пицца",
            "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
            "pizza.png"
        ),
        CategoryDto(
            3,
            "Рыба",
            "Печеная, жареная, сушеная, любая рыба на твой вкус",
            "fish.png"
        ),
        CategoryDto(
            4,
            "Супы",
            "От классики до экзотики: мир в одной тарелке",
            "soup.png"
        ),
        CategoryDto(
            5,
            "Салаты",
            "Хрустящий калейдоскоп под соусом вдохновения",
            "salad.png"
        )
    )

    // Список рецептов бургеров
    private val burgerRecipes = listOf(
        RecipeDto(
            id = 0,
            title = "Классический бургер с говядиной",
            ingredients = listOf(
                IngredientDto(
                    quantity = "0.5",
                    unitOfMeasure = "кг",
                    description = "говяжий фарш"
                ),
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "луковица, мелко нарезанная"
                ),
                IngredientDto(
                    quantity = "2.0",
                    unitOfMeasure = "зубч",
                    description = "чеснок, измельченный"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "булочки для бургера"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "листа салата"
                ),
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "помидор, нарезанный кольцами"
                ),
                IngredientDto(
                    quantity = "2.0",
                    unitOfMeasure = "ст. л.",
                    description = "горчица"
                ),
                IngredientDto(
                    quantity = "2.0",
                    unitOfMeasure = "ст. л.",
                    description = "кетчуп"
                ),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "соль и черный перец"
                )
            ),
            method = listOf(
                "1. В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты.",
                "2. Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки.",
                "3. В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки.",
                "4. Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки.",
                "5. Подавайте бургеры горячими с картофельными чипсами или картофельным пюре."
            ),
            imageUrl = "burger-hamburger.png"
        ),
        RecipeDto(
            id = 1,
            title = "Чизбургер с беконом",
            ingredients = listOf(
                IngredientDto(
                    quantity = "0.4",
                    unitOfMeasure = "кг",
                    description = "говяжий фарш"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "ломтика бекона"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "ломтика сыра чеддер"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "булочки для бургера"
                ),
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "помидор, нарезанный"
                ),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "майонез и кетчуп"
                )
            ),
            method = listOf(
                "1. Обжарьте бекон на сковороде до хрустящей корочки, отложите на бумажное полотенце.",
                "2. Сформируйте из фарша 4 котлеты, обжарьте с каждой стороны по 4 минуты.",
                "3. За минуту до готовности положите на каждую котлету по ломтику сыра, чтобы он расплавился.",
                "4. Соберите бургер: булочка, майонез, котлета с сыром, бекон, помидор, кетчуп.",
                "5. Подавайте горячими."
            ),
            imageUrl = "burger-cheeseburger.png"
        )
    )

    /**
     * Возвращает список всех категорий блюд.
     */
    fun getCategories(): List<CategoryUiModel> {
        return listOf(
            CategoryDto(
                id = 1,
                title = "Выпечка",
                imageUrl = "https://example.com/cakes.jpg",
                description = "Рецепты тортов, пирогов и печенья"
            ),
            CategoryDto(
                id = 2,
                title = "Салаты",
                imageUrl = "https://example.com/salads.jpg",
                description = "Лёгкие и свежие салаты"
            )
        ).map { it.toUiModel() }  // вызов toUiModel() для каждого DTO
    }

    /**
     * Возвращает список рецептов для указанной категории.
     * Имитирует разные API endpoints через конструкцию when.
     *
     * @param categoryId ID категории
     * @return Список рецептов для категории или пустой список, если категория не поддерживается
     */
    fun getRecipesByCategoryId(categoryId: Int): List<RecipeDto> = when (categoryId) {
        0 -> burgerRecipes  // Имитация GET /category/0/recipes — рецепты бургеров
        else -> emptyList() // Остальные категории пока пустые
    }
}