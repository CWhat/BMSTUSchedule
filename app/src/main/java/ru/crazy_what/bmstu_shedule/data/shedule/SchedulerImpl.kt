package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.Lesson
import java.util.*

// TODO мне кажется, это стоит оптимизировать. Как я понял, работа с Calendar не очень быстрая
class SchedulerImpl(
    private val numerator: WeekSchedule, // числитель
    private val denominator: WeekSchedule // знаменатель
) : Scheduler {
    private var currentDate = Calendar.getInstance()

    private val currentSemester = getSemester(currentDate)

    override val numberOfWeeksInSemester: Int = getNumberOfWeeks(currentSemester)
    override val numberOfStudyDaysInSemester: Int = numberOfWeeksInSemester * 6

    override val currentWeek: Int? = getWeek(currentDate)
    override val currentDay: Int? = getCurrentDay(currentDate, currentSemester)

    override fun studyDay(studyDayNum: Int): List<Lesson> {
        if (studyDayNum < 1)
            error("Номер дня должен быть натуральным числом")

        if (studyDayNum > numberOfStudyDaysInSemester)
            error("В этом семестре максимум $numberOfStudyDaysInSemester дней")

        val weekNum = studyDayNum / 6 + 1
        val week = if (weekNum % 2 == 1)
            numerator
        else
            denominator

        val lessonInfoList = when ((studyDayNum - 1) % 6) {
            0 -> week.monday
            1 -> week.tuesday
            2 -> week.wednesday
            3 -> week.thursday
            4 -> week.friday
            5 -> week.saturday
            else -> error("Как такое возможно?")
        }

        return lessonInfoList.map { lessonInfo ->
            val time = CallManager.getTime(lessonInfo.building, lessonInfo.numPair)

            Lesson(
                type = lessonInfo.typeLesson.toString(),
                timeStart = time.first,
                timeEnd = time.second,
                name = lessonInfo.name,
                teacher = lessonInfo.teacher,
                room = lessonInfo.room,
                timeProgress = null, // TODO реализовать сообщение
                messageFromAbove = null,
                messageBelow = null
            )
        }
    }

    override fun studyWeek(weekNum: Int): List<StudyDayInfo> {
        TODO("Not yet implemented")
    }

}