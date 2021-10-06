package ru.crazy_what.bmstu_shedule.data.remote.schedule

import ru.crazy_what.bmstu_shedule.common.Group
import ru.crazy_what.bmstu_shedule.date.toTime
import ru.crazy_what.bmstu_shedule.domain.model.Lesson

data class LessonDto(
    var beginTime: String,
    var endTime: String,
    val name: String,
    val type: String,
    val teachers: String,
    val cabinet: String,
) {

    fun toLesson(groups: List<Group>) = Lesson(
        beginTime = beginTime.toTime(),
        endTime = endTime.toTime(),
        name = name,
        type = type,
        // TODO надо парсить список преподавателей
        teachers = listOf(teachers),
        cabinet = cabinet,
        groups = groups,
    )
}
