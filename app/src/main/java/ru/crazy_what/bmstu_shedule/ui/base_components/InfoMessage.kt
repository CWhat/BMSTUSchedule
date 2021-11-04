package ru.crazy_what.bmstu_shedule.ui.base_components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

// TODO сделать красивым
@Composable
fun InfoMessage(text: String, modifier: Modifier = Modifier) {
    // TODO выбрать размер шрифта
    Text(
        text = text,
        modifier = modifier,
        textAlign = TextAlign.Center,
    )
}