package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.Date
import ru.crazy_what.bmstu_shedule.data.DayOfWeek
import ru.crazy_what.bmstu_shedule.data.Lesson
import ru.crazy_what.bmstu_shedule.data.Month
import java.util.*

// TODO мне кажется, это стоит оптимизировать. Как я понял, работа с Calendar не очень быстрая
// TODO начало семестра и количество дней и недель лучше передавать в конструктор
class SchedulerImpl(
    private val biweeklySchedule: BiweeklySchedule
) : Scheduler {
    private var currentDate = Calendar.getInstance()

    private val currentSemester = getSemester(currentDate)
    private val startSemester = getStartSemester(currentDate, currentSemester)

    override val numberOfWeeksInSemester: Int = getNumberOfWeeks(currentSemester)
    override val numberOfStudyDaysInSemester: Int = numberOfWeeksInSemester * 6

    override val currentWeek: Int? = getWeek(currentDate)

    // TODO кажется, здесь не учитываются выходные
    override val currentDay: Int? = getTimeBetween(startSemester, currentDate) + 1

    override fun studyDay(studyDayNum: Int): List<Lesson> {
        if (studyDayNum < 1)
            error("Номер дня должен быть натуральным числом")

        if (studyDayNum > numberOfStudyDaysInSemester)
            error("В этом семестре максимум $numberOfStudyDaysInSemester дней")

        val weekNum = studyDayNum / 6 + 1
        val week = if (weekNum % 2 == 1)
            biweeklySchedule.numerator
        else
            biweeklySchedule.denominator

        val lessonList = when ((studyDayNum - 1) % 6) {
            0 -> week.monday
            1 -> week.tuesday
            2 -> week.wednesday
            3 -> week.thursday
            4 -> week.friday
            5 -> week.saturday
            else -> error("Как такое возможно?")
        }

        return lessonList
    }

    // TODO сделать проверку на границы
    override fun studyWeekInfo(weekNum: Int): WeekInfo = WeekInfo(
        number = weekNum,
        type = if (weekNum % 2 == 1) WeekType.NUMERATOR else WeekType.DENOMINATOR,
        rangeOfDays = IntRange((weekNum - 1) * 6 + 1, (weekNum - 1) * 6 + 7)
    )

    // TODO сделать проверку на границы
    override fun studyDayInfo(studyDayNum: Int): StudyDayInfo {
        // TODO у меня явно проблемы с называнием переменных
        val studyWeekNum = (studyDayNum - 1) / 6 + 1
        // день относительно начала семестра, но по обычному календарю
        val dayNum = (studyWeekNum - 1) + studyDayNum

        val calendarDate = startSemester.clone() as Calendar
        calendarDate.add(Calendar.DAY_OF_YEAR, dayNum - 1)

        val date = Date(
            year = calendarDate.get(Calendar.YEAR),
            month = Month.from(calendarDate),
            dayOfWeek = DayOfWeek.from(calendarDate),
            dayOfMonth = calendarDate.get(Calendar.DAY_OF_MONTH),
        )
        return StudyDayInfo(
            date, studyDayNum
        )
    }

    // TODO сделать проверку на границы
    override fun whichWeekDoesDayBelong(day: StudyDayInfo): Int = (day.studyDayNum - 1) / 6 + 1

}