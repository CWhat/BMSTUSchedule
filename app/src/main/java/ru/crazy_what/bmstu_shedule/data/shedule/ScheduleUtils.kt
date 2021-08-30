package ru.crazy_what.bmstu_shedule.data.shedule

import java.util.*

// TODO придумать нормально описание
// Здесь находятся базовые функции для работы со временем для расписания

// Виды семестров
enum class Semester {
    Autumn, // Осенний-зимний
    Spring // Зимний-весенний
}

// Выдает вид текущего семестра
// 2 семестр начинается на 24 неделе
internal fun getSemester(date: Calendar): Semester =
    if (getWeekOfSchoolYear(date) >= 24)
        Semester.Spring
    else
        Semester.Autumn

internal fun getNumberOfWeeks(semester: Semester): Int = when (semester) {
    Semester.Autumn -> 23
    Semester.Spring -> 29
}

// Возвращает номер недели относительно начала семестра
fun getWeek(date: Calendar): Int = when (getSemester(date)) {
    Semester.Autumn -> getWeekOfSchoolYear(date)
    Semester.Spring -> getWeekOfSchoolYear(date) - getNumberOfWeeks(Semester.Autumn)
}

// Возвращет номер недели относительно 1 сентября
fun getWeekOfSchoolYear(date: Calendar): Int {
    return if (date.get(Calendar.MONTH) >= Calendar.SEPTEMBER) {
        val september1 = GregorianCalendar(date.get(Calendar.YEAR), Calendar.SEPTEMBER, 1)
        date.get(Calendar.WEEK_OF_YEAR) - september1.get(Calendar.WEEK_OF_YEAR) + 1
    } else {
        val september1 = GregorianCalendar(date.get(Calendar.YEAR) - 1, Calendar.SEPTEMBER, 1)
        val december31 = GregorianCalendar(date.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 31)

        date.get(Calendar.WEEK_OF_YEAR) + december31.get(Calendar.WEEK_OF_YEAR) - september1.get(
            Calendar.WEEK_OF_YEAR
        ) + 1
    }
}