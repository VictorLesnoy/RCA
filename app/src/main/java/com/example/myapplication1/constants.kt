package com.example.myapplication1.navigation

// --- КОНСТАНТЫ ДЛЯ DEEP LINKS ---
const val DEEP_LINK_SCHEME = "recipeapp"          // схема для тестов через ADB и внутренней навигации
const val DEEP_LINK_BASE_URL = "recipes.androidsprint.ru" // домен для шаринга и App Links

// Имя параметра для ID рецепта в маршруте навигации
//const val PARAM_RECIPE_ID = "recipe_id"

/**
 * Создаёт URI для глубокой ссылки на рецепт (кастомная схема).
 * Используется для внутренней навигации и тестов через ADB.
 */
fun createRecipeDeepLink(recipeId: Int): String {
    return "$DEEP_LINK_SCHEME://recipe/$recipeId"
}

/**
 * Создаёт HTTPS-ссылку для шаринга.
 * Используется в ShareUtils и для отправки ссылок в мессенджеры.
 */
fun createHttpShareLink(recipeId: Int): String {
    return "https://$DEEP_LINK_BASE_URL/recipe/$recipeId"
}

