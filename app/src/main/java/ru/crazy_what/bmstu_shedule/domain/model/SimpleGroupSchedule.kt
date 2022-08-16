package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek

// Глупое расписание, как на сайте. По сути, просто таблица
class SimpleGroupSchedule(
    val groupName: String,
    private val lessons: Map<Pair<WeekType, DayOfWeek>, List<NewGroupLesson>>,
) {

    operator fun get(weekType: WeekType, dayOfWeek: DayOfWeek): List<NewGroupLesson> =
        lessons[Pair(weekType, dayOfWeek)] ?: emptyList()
}