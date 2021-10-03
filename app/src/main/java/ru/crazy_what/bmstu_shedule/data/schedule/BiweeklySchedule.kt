package ru.crazy_what.bmstu_shedule.data.schedule

// Расписание на две недели
data class BiweeklySchedule(
    val numerator: WeekSchedule, // числитель
    val denominator: WeekSchedule // знаменатель
)
