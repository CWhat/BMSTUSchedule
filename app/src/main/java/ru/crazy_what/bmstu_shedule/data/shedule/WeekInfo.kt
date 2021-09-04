package ru.crazy_what.bmstu_shedule.data.shedule

import androidx.compose.runtime.Composable

data class WeekInfo(
    val number: Int, // номер недели
    val type: WeekType,
    val rangeOfDays: IntRange
)

@Composable
fun WeekInfo.info(): String = "$number неделя, ${type.toTranslateString()}"