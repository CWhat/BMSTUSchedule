package ru.crazy_what.bmstu_shedule.data.shedule

import androidx.compose.runtime.Composable

enum class WeekType {
    NUMERATOR, DENOMINATOR;

    @Composable
    fun toTranslateString(): String = when(this) {
        NUMERATOR -> "числитель"
        DENOMINATOR -> "знаменатель"
    }
}