package ru.crazy_what.bmstu_shedule.data.shedule

import androidx.compose.runtime.Composable

enum class WeekType {
    NUMERATOR, DENOMINATOR
}

@Composable
fun WeekType.toTranslateString(): String = when(this) {
    WeekType.NUMERATOR -> "числитель"
    WeekType.DENOMINATOR -> "знаменатель"
}