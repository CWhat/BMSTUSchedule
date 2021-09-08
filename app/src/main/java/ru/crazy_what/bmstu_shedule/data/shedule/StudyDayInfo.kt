package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.Date

// TODO возможно, стоит добавить поле, обозначающее является ли день выходным
data class StudyDayInfo(
    val date: Date,
    val studyDayNum: Int
)
