package ru.crazy_what.bmstu_shedule.data.shedule

import java.util.*

class Scheduler(
    private val numerator: StudyWeek, // числитель
    private val denominator: StudyWeek // знаменатель
) {
    private val curDate = Calendar.getInstance()
    private var curWeek = getWeek(curDate)

    fun currentStudyWeek() = studyWeek(curWeek)

    fun nextStudyWeek() {
        curWeek++
        // TODO нужно что-то делать если curWeek стал 53
        studyWeek(curWeek)
    }

    fun prevStudyWeek() {
        curWeek--
        // TODO нужно что-то делать если curWeek стал 0
        studyWeek(curWeek)
    }

    fun studyWeek(num: Int): StudyWeek {
        if (num < 1)
            error("Номер недели должен быть натуральным числом")

        if (num > 52)
            error("В учебном году 52 недели")

        return if (num % 2 == 1)
            numerator
        else
            denominator
    }
}

// Виды семестров
enum class Semester {
    Autumn, // Осенний-зимний
    Spring // Зимний-весенний
}

// Выдает вид текущего семестра
// 2 семестр начинается на 24 неделе
fun getSemester(date: Calendar): Semester =
    if (getWeek(date) >= 24)
        Semester.Spring
    else
        Semester.Spring

// Возвращет номер недели относительно 1 сентября
fun getWeek(date: Calendar): Int {
    if (date.get(Calendar.MONTH) >= Calendar.SEPTEMBER) {
        val september1 = GregorianCalendar(date.get(Calendar.YEAR), Calendar.SEPTEMBER, 1)
        return date.get(Calendar.WEEK_OF_YEAR) - september1.get(Calendar.WEEK_OF_YEAR) + 1
    } else {
        val september1 = GregorianCalendar(date.get(Calendar.YEAR) - 1, Calendar.SEPTEMBER, 1)
        val december31 = GregorianCalendar(date.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 31)

        return date.get(Calendar.WEEK_OF_YEAR) + december31.get(Calendar.WEEK_OF_YEAR) - september1.get(
            Calendar.WEEK_OF_YEAR
        ) + 1
    }
}