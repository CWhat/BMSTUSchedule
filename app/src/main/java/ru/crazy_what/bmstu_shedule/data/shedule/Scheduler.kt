package ru.crazy_what.bmstu_shedule.data.shedule

import java.util.*

class Scheduler(
    private val numerator: StudyWeek, // числитель
    private val denominator: StudyWeek // знаменатель
) {
    private var curDate = Calendar.getInstance()
    private var curWeek = getWeek(curDate)

    private val curSemester = getSemester(curDate)

    val numberOfWeeksInSemester = getNumberOfWeeks(curSemester)
    val numberOfStudyDaysInSemester = numberOfWeeksInSemester * 6

    fun currentStudyWeek() = studyWeek(curWeek)

    fun updateTime(date: Calendar) {
        curDate = date
    }

    fun updateTime() = updateTime(Calendar.getInstance())

    // TODO сделать функцию, которая будет возвращать начало и конец ближайшей следующей пары или текущую, если она сейчас идет
    // TODO сделать функцию, которая возвращает массив дат, соответсвующих каждому дню выбранной учебной недели

    // Возвращает расписание на следующую неделю, если она есть, иначе null
    fun nextStudyWeek(): StudyWeek? {
        if (curWeek == getNumberOfWeeks(curSemester))
            return null

        curWeek++
        return studyWeek(curWeek)
    }

    // Возвращает расписание на предыдущую неделю, если она есть, иначе null
    fun prevStudyWeek(): StudyWeek? {
        if (curWeek == 1)
            return null

        curWeek--
        return studyWeek(curWeek)
    }

    fun studyWeek(weekNum: Int): StudyWeek {
        if (weekNum < 1)
            error("Номер недели должен быть натуральным числом")

        if (curSemester == Semester.Autumn && weekNum > getNumberOfWeeks(Semester.Autumn))
            error("В осеннем семестре ${getNumberOfWeeks(Semester.Autumn)} недели")

        if (curSemester == Semester.Spring && weekNum > getNumberOfWeeks(Semester.Spring))
            error("В весеннем семестре ${getNumberOfWeeks(Semester.Spring)} недель")

        return if (weekNum % 2 == 1)
            numerator
        else
            denominator
    }

    fun studyDay(studyDayNum: Int): List<LessonInfo> {
        if (studyDayNum < 1)
            error("Номер дня должен быть натуральным числом")

        if (studyDayNum > numberOfStudyDaysInSemester)
            error("В этом семестре максимум $numberOfStudyDaysInSemester дней")

        val weekNum = studyDayNum / 6 + 1
        val week = studyWeek(weekNum)

        return when (studyDayNum % 6) {
            0 -> week.monday
            1 -> week.tuesday
            2 -> week.wednesday
            3 -> week.thursday
            4 -> week.friday
            5 -> week.saturday
            else -> error("Как такое возможно?")
        }
    }
}

// Виды семестров
enum class Semester {
    Autumn, // Осенний-зимний
    Spring // Зимний-весенний
}

// Выдает вид текущего семестра
// 2 семестр начинается на 24 неделе
private fun getSemester(date: Calendar): Semester =
    if (getWeekOfSchoolYear(date) >= 24)
        Semester.Spring
    else
        Semester.Autumn

private fun getNumberOfWeeks(semester: Semester): Int = when (semester) {
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