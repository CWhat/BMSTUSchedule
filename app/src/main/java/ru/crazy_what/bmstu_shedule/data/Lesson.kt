package ru.crazy_what.bmstu_shedule.data

data class Lesson(
    val type: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val teacher: String? = null,
    val room: String? = null,
    // TODO это стоит отсюда убрать
    val timeProgress: Float? = null,
    val messageFromAbove: String? = null,
    val messageBelow: String? = null,
)
