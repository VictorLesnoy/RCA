package com.example.myapplication1.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.IntentCompat
import com.example.myapplication1.navigation.Destination

object ShareUtils {

    /**
     * Создаёт Intent для шаринга рецепта.
     *
     * @param context Контекст (лучше брать из Activity или LocalContext.current в Compose)
     * @param recipeId ID рецепта, который нужно зашарить
     */
    fun shareRecipe(context: Context, recipeId: Int): Intent {
        // Используем HTTPS-ссылку специально для шаринга (мессенджеры не открывают recipeapp://)
        val deepLinkUri = Destination.RecipeDetail.createHttpShareLink(recipeId)
        val linkString = deepLinkUri.toString()

        val shareText = buildString {
            append("🍳 Рецепт, который стоит попробовать!\n\n")
            append(linkString)
        }

        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
    }
}