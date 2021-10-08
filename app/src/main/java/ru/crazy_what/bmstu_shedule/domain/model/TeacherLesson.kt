package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.common.Group

data class TeacherLesson(
    val info: LessonInfo,
    val groups: List<Group>,
)
