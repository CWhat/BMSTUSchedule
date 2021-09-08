package ru.crazy_what.bmstu_shedule.data

data class Lesson(
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val type: String? = null,
    val teacher: String? = null,
    val room: String? = null,
)

// TODO поменять на это
/*data class Lesson(
    val timeStart: Time,
    val timeEnd: Time,
    val name: String,
    val type: String? = null,
    val teacher: String? = null,
    val room: String? = null,
)*/