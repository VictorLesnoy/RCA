package com.example.myapplication1

// Имя параметра для ID рецепта в маршруте навигации
const val PARAM_RECIPE_ID = "recipe_id"

/**
 * Создаёт URI для глубокой ссылки на рецепт.
 * Поддерживает обе схемы: recipeapp:// и https://recipes.androidsprint.ru
 */
fun createRecipeDeepLink(recipeId: Int): String {
    // Вариант для кастомной схемы (удобно для тестов через ADB)
    return "recipeapp://recipe/$recipeId"

    // Если нужно именно для HTTPS-домена, раскомментируй строку ниже и закомментируй верхнюю:
    // return "https://recipes.androidsprint.ru/recipe/$recipeId"
}
