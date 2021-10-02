package ru.crazy_what.bmstu_shedule.ui.schedule_editor.components

import androidx.compose.runtime.Composable

data class LessonSource(
    var timeStart: String,
    var timeEnd: String,
    val name: String,
    val type: String,
    val teachers: String,
    val room: String,
)

@Composable
fun LessonEditor(
    numerator: LessonSource,
    denominator: LessonSource,
    numeratorChange: (newSource: LessonSource) -> Unit,
    denominatorChange: (newSource: LessonSource) -> Unit,
) {

}