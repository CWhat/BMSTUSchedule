package ru.crazy_what.bmstu_shedule.date

import java.util.*

data class Date(
    val year: Int,
    val month: Month,
    val dayOfWeek: DayOfWeek, // TODO это должно вычисляться
    val dayOfMonth: Int
)

fun Calendar.toDate() = Date(
    year = this.get(Calendar.YEAR),
    month = Month.from(this),
    dayOfWeek = DayOfWeek.from(this),
    dayOfMonth = this.get(Calendar.DAY_OF_MONTH),
)