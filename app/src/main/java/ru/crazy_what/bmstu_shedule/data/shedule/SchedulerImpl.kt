package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.DayOfWeek
import ru.crazy_what.bmstu_shedule.data.Lesson
import ru.crazy_what.bmstu_shedule.data.Month
import ru.crazy_what.bmstu_shedule.data.mutableListWithCapacity
import java.util.*

// TODO мне кажется, это стоит оптимизировать. Как я понял, работа с Calendar не очень быстрая
class SchedulerImpl(
    private val biweeklySchedule: BiweeklySchedule
) : Scheduler {
    private var currentDate = Calendar.getInstance()

    private val currentSemester = getSemester(currentDate)
    private val startSemester = getStartSemester(currentDate, currentSemester)

    override val numberOfWeeksInSemester: Int = getNumberOfWeeks(currentSemester)
    override val numberOfStudyDaysInSemester: Int = numberOfWeeksInSemester * 6

    override val currentWeek: Int? = getWeek(currentDate)

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

    // TODO сделать проверку на границы
    override fun studyWeekInfo(weekNum: Int): WeekInfo = WeekInfo(
        number = weekNum,
        type = if (weekNum % 2 == 1) WeekType.NUMERATOR else WeekType.DENOMINATOR,
        rangeOfDays = IntRange((weekNum - 1) * 6 + 1, (weekNum - 1) * 6 + 7)
    )

    // TODO сделать проверку на границы
    override fun studyDayInfo(studyDayNum: Int): StudyDayInfo {
        //TODO("Not yet implemented")
        val studyWeekNum = studyDayNum / 6 + 1
        // день относительно начала семестра, но по обычному календарю
        val dayNum = studyWeekNum + ((studyDayNum - 1) % 6)

        val date = startSemester.clone() as Calendar
        date.add(Calendar.DAY_OF_YEAR, dayNum - 1)
        return StudyDayInfo(
            year = date.get(Calendar.YEAR),
            month = Month.from(date),
            dayOfWeek = DayOfWeek.from(date),
            dayOfMonth = date.get(Calendar.DAY_OF_MONTH),
            studyDayNum = studyDayNum
        )
    }

    // TODO сделать проверку на границы
    override fun whichWeekDoesDayBelong(day: StudyDayInfo): Int = day.studyDayNum / 6 + 1

    // TODO как-то неправильно работает
    override fun studyWeek(weekNum: Int): List<StudyDayInfo> {
        val startWeek = (startSemester.clone() as Calendar)
        startWeek.add(Calendar.WEEK_OF_YEAR, weekNum - 1)
        var startStudyDayNum = ((weekNum - 1) * 6) + 1

        val res = mutableListWithCapacity<StudyDayInfo>(6)
        for (i in 0..5) {
            res.add(
                StudyDayInfo(
                    startWeek.get(Calendar.YEAR),
                    Month.from(startWeek),
                    DayOfWeek.from(startWeek),
                    startWeek.get(Calendar.DAY_OF_MONTH),
                    startStudyDayNum++
                )
            )

            startWeek.add(Calendar.DAY_OF_WEEK, 1)
        }

        return res
    }

}