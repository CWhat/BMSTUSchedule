package ru.crazy_what.bmstu_shedule.data.shedule

data class LessonInfo(
    val typeLesson: TypeLesson,
    val building: Building,
    val numPair: Int,
    val name: String,
    val teacher: String? = null, // преподаватель не указан, смотри на кафедре или сходи на пару
    val room: String? = null, // кабинет не указан, смотри на кафедре
)

enum class TypeLesson {
    Seminar, Lecture, Lab, None; // скорее всего, есть еще типы занятий

    override fun toString(): String = when (this) {
        Seminar -> "сем"
        Lecture -> "лек"
        Lab -> "лаб"
        None -> ""
    }
}

fun getTypeLesson(type: String?): TypeLesson = when (type) {
    "сем" -> TypeLesson.Seminar
    "лек" -> TypeLesson.Lecture
    "лаб" -> TypeLesson.Lab
    null -> TypeLesson.None
    else -> error("Неподдерживаемый тип занятия: $type")
}