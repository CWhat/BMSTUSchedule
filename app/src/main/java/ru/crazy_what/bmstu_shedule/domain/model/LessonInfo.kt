package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Time

// TODO название не самое подходящее
data class LessonInfo(
    val weekType: WeekType,
    val dayOfWeek: DayOfWeek,
    val beginTime: Time,
    val endTime: Time,
    val type: String,
    val name: String,
    val cabinet: String,
)
