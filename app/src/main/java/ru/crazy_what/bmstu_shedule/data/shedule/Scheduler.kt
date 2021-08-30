package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.Lesson
import java.util.*

// TODO переименовать
// Тут отсчет идет с единицы, мне показалось это логичным
interface SchedulerInterface {

    val numberOfStudyDaysInSemester: Int
    val numberOfWeeksInSemester: Int

    // null, если сейчас другой семестр
    // возвращаются именно номера относительно первой недели или первого дня этого семестра
    val currentWeek: Int?
    val currentDay: Int?

    fun studyDay(studyDayNum: Int): List<Lesson>
    fun studyWeek(weekNum: Int): List<StudyDayInfo>

}

// TODO реализовать интерфейс сверху
// TODO переименовать
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