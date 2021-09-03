package ru.crazy_what.bmstu_shedule.data.shedule

// Расписание на две недели
data class BiweeklySchedule(
    val numerator: WeekSchedule, // числитель
    val denominator: WeekSchedule // знаменатель
)
