package ru.crazy_what.bmstu_shedule.data

enum class DateCircleState {
    SELECT, CURRENT, NONE
}

data class Date(
    val dayOfWeek: String,
    val day: Int,
    val state: DateCircleState = DateCircleState.NONE
)
