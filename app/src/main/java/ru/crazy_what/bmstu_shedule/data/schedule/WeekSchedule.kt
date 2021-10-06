package ru.crazy_what.bmstu_shedule.data.schedule

import ru.crazy_what.bmstu_shedule.data.remote.schedule.LessonDto

// TODO можно заменить на отображение "день недели -> список занятий"
// Расписание на неделю
data class WeekSchedule(
    val monday: List<LessonDto>,
    val tuesday: List<LessonDto>,
    val wednesday: List<LessonDto>,
    val thursday: List<LessonDto>,
    val friday: List<LessonDto>,
    val saturday: List<LessonDto>
)
