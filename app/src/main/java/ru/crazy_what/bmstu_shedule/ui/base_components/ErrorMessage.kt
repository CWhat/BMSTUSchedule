package ru.crazy_what.bmstu_shedule.ui.base_components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorMessage(text: String, modifier: Modifier = Modifier) {
    Text(
        text = "При загрузке закладок произошла ошибка: $text",
        modifier = modifier,
        textAlign = TextAlign.Center,
    )
}