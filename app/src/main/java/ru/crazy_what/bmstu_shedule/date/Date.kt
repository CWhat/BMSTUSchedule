package ru.crazy_what.bmstu_shedule.date

data class Date(
    val year: Int,
    val month: Month,
    val dayOfWeek: DayOfWeek, // TODO это должно вычисляться
    val dayOfMonth: Int
)
