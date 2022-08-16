package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek

// Глупое расписание, как на сайте. По сути, просто таблица
data class SimpleGroupSchedule(
    val groupName: String,
    val lessons: Map<Pair<WeekType, DayOfWeek>, List<GroupLesson>>,
) {

    operator fun get(weekType: WeekType, dayOfWeek: DayOfWeek): List<GroupLesson> =
        lessons[Pair(weekType, dayOfWeek)] ?: emptyList()
}