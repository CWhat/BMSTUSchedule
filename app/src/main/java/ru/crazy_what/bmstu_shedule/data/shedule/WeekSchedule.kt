package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.Lesson

// Расписание на неделю
data class WeekSchedule(
    val monday: List<Lesson>,
    val tuesday: List<Lesson>,
    val wednesday: List<Lesson>,
    val thursday: List<Lesson>,
    val friday: List<Lesson>,
    val saturday: List<Lesson>
)
