package ru.crazy_what.bmstu_shedule.data.schedule

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.date.Date
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.toDate
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduler
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.LessonsListState
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.model.LessonWithInfo
import java.util.*

class GroupSchedulerImpl(
    private val groupName: String,
    private val groupSchedule: GroupSchedule,
) : GroupScheduler {
    private var currentDate = Calendar.getInstance()

    private val currentSemester = getSemester(currentDate)
    private val startSemester = getStartSemester(currentDate, currentSemester)

    override val numberOfStudyDaysInSemester: Int = getNumberOfWeeks(currentSemester) * 6

    override val initDay: Int
        get() {
            val between = if (currentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                val newDate = currentDate.clone() as Calendar
                newDate.add(Calendar.DAY_OF_YEAR, 1)
                return getTimeBetween(startSemester, newDate)
            } else {
                getTimeBetween(startSemester, currentDate)
            }
            if (between < 0) return 0

            val weekNum = between / 7 + 1
            return (between + 1) - (weekNum - 1)
        }
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

    override fun getSchedule(dayNum: Int): Flow<LessonsListState> = flow {
        if (dayNum < 0)
            error("Номер дня должен быть неотрицательным числом")

        if (dayNum >= numberOfStudyDaysInSemester)
            error("В этом семестре максимум $numberOfStudyDaysInSemester дней")

        val weekNum = dayNum / 6
        val weekType = if (weekNum % 2 == 0)
            WeekType.NUMERATOR
        else
            WeekType.DENOMINATOR

        val dayOfWeek = when (dayNum % 6) {
            0 -> DayOfWeek.MONDAY
            1 -> DayOfWeek.TUESDAY
            2 -> DayOfWeek.WEDNESDAY
            3 -> DayOfWeek.THURSDAY
            4 -> DayOfWeek.FRIDAY
            5 -> DayOfWeek.SATURDAY
            else -> error("Как такое возможно?")
        }

        // TODO опасно
        emit(
            LessonsListState.Lessons(
                groupSchedule.lessons[Pair(
                    weekType,
                    dayOfWeek
                )]!!.map { groupLesson ->
                    LessonWithInfo(groupLesson)
                })
        )
    }

    override fun getDate(dayNum: Int): Date {
        val weekNum = dayNum / 6
        val days = weekNum * 7 + (dayNum % 6)
        val newDate = startSemester.clone() as Calendar
        newDate.add(Calendar.DAY_OF_YEAR, days)
        return newDate.toDate()
    }

    // TODO что делать с переводом?
    override fun weekInfo(weekNum: Int): String =
        "${weekNum + 1} неделя, ${if (weekNum % 2 == 0) "числитель" else "знаменатель"}"
}