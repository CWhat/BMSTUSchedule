package ru.crazy_what.bmstu_shedule.data.remote.model

data class GroupLesson(
    val isNumerator: Boolean,
    val day: Int,
    val startAt: String,
    val endAt: String,
    val name: String,
    val cabinet: String,
    val teachers: List<String>,
    val type: String,
)
