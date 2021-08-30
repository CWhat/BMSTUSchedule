package ru.crazy_what.bmstu_shedule.data.shedule

import java.time.Month
import java.time.Year

data class StudyDayInfo(
    val year: Year,
    val month: Month,
    val dayOfMonth: Int,
    val studyDayNum: Int
)
