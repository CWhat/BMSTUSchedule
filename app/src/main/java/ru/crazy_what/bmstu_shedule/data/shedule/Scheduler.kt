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
    val calendar = GregorianCalendar(date.get(Calendar.YEAR), Calendar.SEPTEMBER, 1)
    //return date.weekYear - calendar.weekYear + 1
    return date.get(Calendar.WEEK_OF_YEAR) - calendar.get(Calendar.WEEK_OF_YEAR) + 1
}