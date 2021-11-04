package ru.crazy_what.bmstu_shedule.ui.screen.load_schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadProgress(current: Int, total: Int) {
    Column(modifier = Modifier
        .padding(vertical = 16.dp), verticalArrangement = Arrangement.Center) {
        Text(text = "Загружено расписаний: $current из $total.")
        LinearProgressIndicator(
            modifier = Modifier.padding(16.dp),
            progress = current.toFloat() / total,
            // TODO хардкод цветов
            color = Color.Green,
            backgroundColor = MaterialTheme.colors.primary,
        )

    }
}