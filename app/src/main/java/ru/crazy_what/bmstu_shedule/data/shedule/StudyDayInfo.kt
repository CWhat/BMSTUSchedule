package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.DayOfWeek
import ru.crazy_what.bmstu_shedule.data.Month

// TODO возможно, стоит добавить поле, обозначающее является ли день выходным
data class StudyDayInfo(
    val year: Int,
    val month: Month,
    val dayOfWeek: DayOfWeek,
    val dayOfMonth: Int,
    val studyDayNum: Int
)
