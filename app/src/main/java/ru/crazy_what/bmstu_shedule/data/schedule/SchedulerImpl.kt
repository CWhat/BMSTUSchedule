package ru.crazy_what.bmstu_shedule.data.schedule

import ru.crazy_what.bmstu_shedule.date.Date
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Month
import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.repository.Scheduler
import java.util.*

// TODO мне кажется, это стоит оптимизировать. Как я понял, работа с Calendar не очень быстрая
// TODO начало семестра и количество дней и недель лучше передавать в конструктор
class SchedulerImpl(
    private val groupName: String,
    private val groupSchedule: GroupSchedule,
) : Scheduler {
    private var currentDate = Calendar.getInstance()

    private val currentSemester = getSemester(currentDate)
    private val startSemester = getStartSemester(currentDate, currentSemester)

    override val numberOfWeeksInSemester: Int = getNumberOfWeeks(currentSemester)
    override val numberOfStudyDaysInSemester: Int = numberOfWeeksInSemester * 6

    override val currentWeek: Int? = getWeek(currentDate)

    override val currentDay: Int?
        get() {
            if (currentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                return null
            else {
                val between = getTimeBetween(startSemester, currentDate)
                if (between < 0) return null

                val weekNum = between / 7 + 1
                return (between + 1) - (weekNum - 1)
            }
        }

    override fun studyDay(studyDayNum: Int): List<GroupLesson> {
        if (studyDayNum < 1)
            error("Номер дня должен быть натуральным числом")

        if (studyDayNum > numberOfStudyDaysInSemester)
            error("В этом семестре максимум $numberOfStudyDaysInSemester дней")

        val weekNum = studyDayNum / 6 + 1
        val weekType = if (weekNum % 2 == 1)
            WeekType.NUMERATOR
        else
            WeekType.DENOMINATOR

        val dayOfWeek = when ((studyDayNum - 1) % 6) {
            0 -> DayOfWeek.MONDAY
            1 -> DayOfWeek.TUESDAY
            2 -> DayOfWeek.WEDNESDAY
            3 -> DayOfWeek.THURSDAY
            4 -> DayOfWeek.FRIDAY
            5 -> DayOfWeek.SATURDAY
            else -> error("Как такое возможно?")
        }

        // TODO опасно
        return groupSchedule.lessons[Pair(weekType, dayOfWeek)]!!
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