package ru.crazy_what.bmstu_shedule.data.schedule

import ru.crazy_what.bmstu_shedule.data.Lesson

// TODO можно заменить на отображение "день недели -> список занятий"
// Расписание на неделю
data class WeekSchedule(
    val monday: List<Lesson>,
    val tuesday: List<Lesson>,
    val wednesday: List<Lesson>,
    val thursday: List<Lesson>,
    val friday: List<Lesson>,
    val saturday: List<Lesson>
)
