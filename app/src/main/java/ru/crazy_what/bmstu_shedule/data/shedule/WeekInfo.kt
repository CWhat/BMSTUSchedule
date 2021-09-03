package ru.crazy_what.bmstu_shedule.data.shedule

data class WeekInfo(
    val number: Int, // номер недели
    val type: WeekType,
    val rangeOfDays: IntRange
)
