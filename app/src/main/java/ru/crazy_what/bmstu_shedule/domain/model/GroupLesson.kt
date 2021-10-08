package ru.crazy_what.bmstu_shedule.domain.model

data class GroupLesson(
    val info: LessonInfo,
    val teachers: List<String>,
)
