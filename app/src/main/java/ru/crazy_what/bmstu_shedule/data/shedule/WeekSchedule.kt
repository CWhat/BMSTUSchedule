package ru.crazy_what.bmstu_shedule.data.shedule

// Расписание на неделю
data class WeekSchedule(
    val monday: List<LessonInfo>,
    val tuesday: List<LessonInfo>,
    val wednesday: List<LessonInfo>,
    val thursday: List<LessonInfo>,
    val friday: List<LessonInfo>,
    val saturday: List<LessonInfo>
)
