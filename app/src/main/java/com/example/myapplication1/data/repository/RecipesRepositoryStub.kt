package com.example.myapplication1.data.repository

import com.example.myapplication1.domain.model.RecipeDto

object RecipesRepositoryStub {

    private val recipes = listOf(
        RecipeDto(
            id = 1,
            title = "Борщ",
            imageUrl = "https://via.placeholder.com/600x400/FF5733/FFFFFF?text=Borscht",
            servings = 4,
            ingredients = listOf(
                "свёкла — 2 шт",
                "говядина — 400 г",
                "капуста — 300 г",
                "картофель — 3 шт",
                "морковь — 1 шт",
                "лук — 1 шт"
            ),
            method = listOf(
                "1. Нарежьте говядину и варите 1 час.",
                "2. Обжарьте лук и морковь до золотистого цвета.",
                "3. Добавьте свёклу и тушите 10 минут.",
                "4. Положите картофель и капусту, варите до готовности.",
                "5. Посолите, добавьте специи и дайте настояться."
            )
        ),
        RecipeDto(
            id = 2,
            title = "Паста карбонара",
            imageUrl = "https://via.placeholder.com/600x400/3366FF/FFFFFF?text=Carbonara",
            servings = 2,
            ingredients = listOf(
                "спагетти — 250 г",
                "бекон — 150 г",
                "яйца — 2 шт",
                "сыр пармезан — 50 г",
                "чеснок — 1 зубчик"
            ),
            method = listOf(
                "1. Отварите спагетти до состояния al dente.",
                "2. Обжарьте бекон и чеснок до хрустящей корочки.",
                "3. Смешайте яйца и тёртый сыр.",
                "4. Смешайте пасту с яично-сырной смесью и беконом.",
                "5. Посыпьте сверху сыром и подавайте сразу."
            )
        )
    )

    fun getRecipeById(id: Int): RecipeDto? = recipes.find { it.id == id }
}