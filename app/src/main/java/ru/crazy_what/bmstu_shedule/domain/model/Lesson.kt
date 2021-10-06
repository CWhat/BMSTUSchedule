package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.common.Group
import ru.crazy_what.bmstu_shedule.date.Time

data class Lesson(
    val beginTime: Time,
    val endTime: Time,
    val type: String,
    val name: String,
    val cabinet: String,
    val groups: List<Group>,
    val teachers: List<String>,
)
