package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.date.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek

// Глупое расписание, как на сайте. По сути, просто таблица
data class SimpleSchedule<T>(
    val groupName: String,
    val uuid: String,
    val lessons: Map<Pair<WeekType, DayOfWeek>, List<T>>,
) {

    operator fun get(weekType: WeekType, dayOfWeek: DayOfWeek): List<T> =
        lessons[Pair(weekType, dayOfWeek)] ?: emptyList()
}

typealias SimpleGroupSchedule = SimpleSchedule<GroupLesson>
// TODO добавить расписание преподавателя