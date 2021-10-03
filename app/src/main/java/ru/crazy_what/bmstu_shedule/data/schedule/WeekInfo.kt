package ru.crazy_what.bmstu_shedule.data.schedule

import androidx.compose.runtime.Composable

data class WeekInfo(
    val number: Int, // номер недели
    val type: WeekType,
    val rangeOfDays: IntRange
) {

    @Composable
    fun info(): String = "$number неделя, ${type.toTranslateString()}"
}