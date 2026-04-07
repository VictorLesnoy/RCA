package com.example.myapplication1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val RecipesAppLightColorScheme = lightColorScheme(
    primary = PrimaryColor, // заголовки секций, основные акценты
    onPrimary = Color.White,

    error = AccentColor, // кнопка «Избранное»
    onError = Color.White,

    tertiary = AccentBlue, // кнопка «Категории», слайдер
    onTertiary = Color.White,
    tertiaryContainer = SliderTrackColor, // дорожка слайдера
    onTertiaryContainer = Color(0xFF210037),

    background = BackgroundColor, // общий фон приложения
    onBackground = TextPrimaryColor,

    surface = SurfaceColor, // карточки, всплывающие окна
    onSurface = TextPrimaryColor,
    surfaceVariant = SurfaceVariantColor,
    onSurfaceVariant = TextSecondaryColor,

    outline = DividerColor, // разделители

    // Дополнительные роли
    secondary = PurpleGrey40,
    onSecondary = Color.White
)

val RecipesAppDarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark, // заголовки секций (тёмный режим)
    onPrimary = Color.Black,

    error = AccentColorDark, // кнопка «Избранное» (тёмный режим)
    onError = Color.Black,

    tertiary = AccentBlueDark, // кнопка «Категории», слайдер (тёмный режим)
    onTertiary = Color.Black,
    tertiaryContainer = SliderTrackColorDark, // дорожка слайдера (тёмный режим)
    onTertiaryContainer = Color(0xFFDACBFF),

    background = BackgroundColorDark, // общий фон (тёмный режим)
    onBackground = TextPrimaryColorDark,

    surface = SurfaceColorDark, // карточки (тёмный режим)
    onSurface = TextPrimaryColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    onSurfaceVariant = TextSecondaryColorDark,

    outline = DividerColorDark, // разделители (тёмный режим)

    // Дополнительные роли (тёмный режим)
    secondary = PurpleGrey80,
    onSecondary = Color.Black
)

@Composable
fun RecipesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),

    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        RecipesAppDarkColorScheme
    } else {
        RecipesAppLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = recipesAppTypography,
        content = content
    )
}