package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek

data class GroupSchedule(
    val groupName: String,
    val lessons: Map<Pair<WeekType, DayOfWeek>, List<GroupLesson>>,
)
