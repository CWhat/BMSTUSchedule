package ru.crazy_what.bmstu_shedule.data.remote.model

data class GroupSchedule(
    val group: Group,
    val isNumeratorFirst: Boolean,
    val lessons: List<GroupLesson>,
    val semesterStart: String,
    val semesterEnd: String,
)
