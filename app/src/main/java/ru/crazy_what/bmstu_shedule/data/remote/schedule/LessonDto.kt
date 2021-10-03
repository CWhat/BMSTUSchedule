package ru.crazy_what.bmstu_shedule.data.remote.schedule

data class LessonDto(
    var timeStart: String,
    var timeEnd: String,
    val name: String,
    val type: String,
    val teachers: String,
    val room: String,
    //val groups: String,
)
