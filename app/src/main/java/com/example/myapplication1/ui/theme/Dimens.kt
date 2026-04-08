package com.example.myapplication1.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object `Dimens` {

    // === ОТСТУПЫ (PADDING / MARGIN) ===
    object Padding {
        val PaddingSmallest: Dp = 2.dp
        val PaddingSmall: Dp = 4.dp
        val PaddingMedium: Dp = 8.dp
        val PaddingMediumLarge: Dp = 12.dp
        val PaddingMain: Dp = 16.dp
        val PaddingLarge: Dp = 24.dp
    }

    // === ВЫСОТЫ ЭКРАНОВ И КОНТЕЙНЕРОВ (HEIGHTS) ===
    object Heights {
        val AppBar: Dp = 56.dp
        val BottomNavigation: Dp = 56.dp
        val ListItem: Dp = 64.dp
        val DialogMinHeight: Dp = 200.dp
        val ScreenMinHeight: Dp = 400.dp
    }

    // === ПАРАМЕТРЫ ТЕНЕЙ (SHADOW) ===
    object Shadows {
        val ElevationSmall: Dp = 1.dp
        val ElevationMedium: Dp = 4.dp
        val ElevationLarge: Dp = 8.dp
        val MaxBlurRadius: Dp = 12.dp
    }

    // === РАЗМЕРЫ ЭЛЕМЕНТОВ (SIZES) ===
    object Sizes {
        // Иконки
        val IconSmall: Dp = 18.dp
        val IconMedium: Dp = 24.dp
        val IconLarge: Dp = 32.dp

        // Изображения
        val ImageSmall: Dp = 40.dp
        val ImageMedium: Dp = 64.dp
        val ImageLarge: Dp = 96.dp

        // Круги/аватарки
        val CircleSmall: Dp = 32.dp
        val CircleMedium: Dp = 48.dp
        val CircleLarge: Dp = 72.dp

        // Прочие элементы
        val DividerHeight: Dp = 1.dp
        val SwitchWidth: Dp = 52.dp
        val SwitchHeight: Dp = 32.dp
    }

    // === ПАРАМЕТРЫ КНОПОК (BUTTONS) ===
    object Button {
        val MinWidth: Dp = 88.dp
        val MinHeight: Dp = 48.dp
        val CornerRadius: Dp = 8.dp
        val OutlinedStrokeWidth: Dp = 1.dp
        val FabSize: Dp = 56.dp
        val ExtendedFabHeight: Dp = 48.dp
    }

    // === ПАРАМЕТРЫ СЛАЙДЕРА (SLIDER) ===
    object Slider {
        val TrackHeight: Dp = 4.dp
        val ThumbRadius: Dp = 10.dp
        val ActiveTrackColorAlpha: Float = 0.8f
        val InactiveTrackColorAlpha: Float = 0.3f
        val ThumbBorderWidth: Dp = 2.dp
    }

    // === ДОПОЛНИТЕЛЬНЫЕ УНИВЕРСАЛЬНЫЕ РАЗМЕРЫ ===
    object Additional {
        val BorderWidth: Dp = 1.dp
        val FocusBorderWidth: Dp = 2.dp
        val SpacerSmall: Dp = 4.dp
        val SpacerMedium: Dp = 8.dp
        val SpacerLarge: Dp = 16.dp
    }
}