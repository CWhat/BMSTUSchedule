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

fun getCurrentDay(currentDate: Calendar, semester: Semester): Int {
    val startSemester = if (semester == Semester.Spring) {
        val february8 = if ((currentDate.get(Calendar.MONTH) > Calendar.FEBRUARY) ||
            (currentDate.get(Calendar.MONTH) == Calendar.FEBRUARY && currentDate.get(Calendar.DAY_OF_MONTH) >= 8)
        )
            GregorianCalendar(currentDate.get(Calendar.YEAR), Calendar.FEBRUARY, 8)
        else
            GregorianCalendar(currentDate.get(Calendar.YEAR), Calendar.FEBRUARY, 8)

        getMonday(february8)
    } else {
        val september1 = if (currentDate.get(Calendar.MONTH) >= Calendar.SEPTEMBER)
            GregorianCalendar(currentDate.get(Calendar.YEAR), Calendar.SEPTEMBER, 1)
        else
            GregorianCalendar(currentDate.get(Calendar.YEAR) - 1, Calendar.SEPTEMBER, 1)

        getMonday(september1)
    }

    return getTimeBetween(startSemester, currentDate)
}

// Возвращает разницу в днях между датами
fun getTimeBetween(leftBound: Calendar, rightBound: Calendar): Int {
    val millisecondsPerDay = (24 * 60 * 60 * 1000).toLong()

    return ((rightBound.timeInMillis - leftBound.timeInMillis) / millisecondsPerDay).toInt()
}

// Возвращает предыдущий понедельник
fun getMonday(date: Calendar): Calendar {
    val dayOfWeek = Calendar.MONDAY
    val weekday = date.get(Calendar.DAY_OF_WEEK)

    // calculate how much to add
    var days = dayOfWeek - weekday
    if (days < 0) days += 7

    val res = date.clone() as Calendar
    res.add(Calendar.DAY_OF_YEAR, days)
    return res
}