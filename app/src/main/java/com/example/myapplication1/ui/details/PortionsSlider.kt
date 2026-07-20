package com.example.myapplication1.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication1.ui.theme.Dimens

@Composable
fun PortionsSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    maxServings: Int = 10,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Порций: $value",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "$maxServings макс.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Slider(
            value = value.toFloat(),
            onValueChange = { newValueFloat ->
                onValueChange(newValueFloat.roundToInt())
            },
            steps = 0,
            valueRange = 1f..maxServings.toFloat(),
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Кнопка "-"
            OutlinedButton(
                onClick = { if (value > 1) onValueChange(value - 1) },
                enabled = value > 1,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("-")
            }

            // Кнопка "+"
            OutlinedButton(
                onClick = { if (value < maxServings) onValueChange(value + 1) },
                enabled = value < maxServings,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("+")
            }
        }
    }
}