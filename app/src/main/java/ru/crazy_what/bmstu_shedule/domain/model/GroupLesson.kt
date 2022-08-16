package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.date.Time

data class GroupLesson(
    private val baseLesson: BaseLessonInfo,
    val teacher: String?,
    val cabinet: String?,
) {
    constructor(
        begin: Time,
        end: Time,
        name: String,
        type: String? = null,
        teacher: String? = null,
        cabinet: String? = null,
    ) : this(BaseLessonInfo(begin, end, name, type), teacher, cabinet)

    val begin: Time
        get() = baseLesson.begin

    val end: Time
        get() = baseLesson.end

    val name: String
        get() = baseLesson.name

    val type: String?
        get() = baseLesson.type
}

data class BaseLessonInfo(
    val begin: Time,
    val end: Time,
    val name: String,
    val type: String?,
)

data class GroupLessonWithInfo(
    private val groupLesson: GroupLesson,
    val timeProgress: Float? = null,
    val messageFromAbove: String? = null,
    val messageBelow: String? = null,
) {

    val begin: Time
        get() = groupLesson.begin

    val end: Time
        get() = groupLesson.end

    val name: String
        get() = groupLesson.name

    val type: String?
        get() = groupLesson.type

    val teacher: String?
        get() = groupLesson.teacher

    val cabinet: String?
        get() = groupLesson.cabinet

}
