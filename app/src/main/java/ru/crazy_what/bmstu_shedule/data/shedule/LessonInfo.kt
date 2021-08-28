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
    Seminar, Lecture, Lab // скорее всего, есть еще типы занятий
}

// Учебная неделя
data class StudyWeek(
    val monday: List<LessonInfo>,
    val tuesday: List<LessonInfo>,
    val wednesday: List<LessonInfo>,
    val thursday: List<LessonInfo>,
    val friday: List<LessonInfo>,
    val saturday: List<LessonInfo>
)
