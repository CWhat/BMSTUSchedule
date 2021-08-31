package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.Month

// TODO возможно, стоит добавить день недели
data class StudyDayInfo(
    val year: Int,
    val month: Month,
    val dayOfMonth: Int,
    val studyDayNum: Int
)
