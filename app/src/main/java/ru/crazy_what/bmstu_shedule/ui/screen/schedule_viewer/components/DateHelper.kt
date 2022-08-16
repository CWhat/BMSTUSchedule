package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.data.schedule.getMonday
import ru.crazy_what.bmstu_shedule.data.schedule.getTimeBetween
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import java.util.*

// TODO написать тесты
// TODO стоит отличать начальный день и текущий день, так как текущий день может не отображаться в календаре
// TODO стоит отдельно проверить, что будет, если currentDate окажется воскресеньем
// Здесь предполагается, что неделя начинается с понедельника (в некоторых странах она начинается с
// субботы или воскресенья)
class DateHelper(
    startDate: Calendar,
    // Этот день включительно
    endDate: Calendar,
    currentDate: Calendar,
    private val isNumeratorFirst: Boolean = true,
) {

    private val start: Calendar
    private val end: Calendar

    private val startMonday: Calendar
    private val endMonday: Calendar

    // На сколько дней начало семестра отличается от понедельника
    // 0, если start - понедельник
    private val startOffset: Int

    // TODO проверять в init, что currentDate лежит между startDate и endDate

    // Кол-во недель
    val daysCount: Int

    // Кол-во недель
    val weeksCount: Int

    // Номер текущего дня
    val currentNumDay: Int
    val currentNumWeek: Int

    // Устанавливает часы, минуты, секунды и миллисекунды в ноль
    private fun Calendar.setNullHourMinSecMillis() {
        this.set(Calendar.HOUR_OF_DAY, 0)
        this.set(Calendar.MINUTE, 0)
        this.set(Calendar.SECOND, 0)
        this.set(Calendar.MILLISECOND, 0)
    }

    init {
        start = (startDate.clone() as Calendar).apply {
            this.setNullHourMinSecMillis()
        }
        end = (endDate.clone() as Calendar).apply {
            this.setNullHourMinSecMillis()
        }

        // В приложении-оригинале семестр начинается с понедельника
        startMonday = getMonday(start)
        endMonday = getMonday(end)

        startOffset = getTimeBetween(startMonday, start)

        // TODO кажется, я где-то ошибся. Мб прибавлять нужно для аргумента week7to6
        daysCount = week7to6(getTimeBetween(start, end)) + 1

        // TODO мб надо прибавить еще одну неделю
        weeksCount = getTimeBetween(startMonday, endMonday) / 7 + 1
        currentNumDay = week7to6(getTimeBetween(start, currentDate))
        currentNumWeek = weekNum(currentNumDay)
    }

    private fun dateToDayNum(date: Calendar): Int = week7to6(getTimeBetween(start, date))

    fun dayNumToDate(dayNum: Int): Calendar =
        (start.clone() as Calendar).apply { this.add(Calendar.DATE, week6to7(dayNum)) }

    // номер недели, соответствующей "6-дневной" неделе
    fun weekNum(dayNum: Int): Int = (dayNum + startOffset) / 6

    // переводит номер дня "7-дневной" недели в "6-дневную" (номера воскресений не учитываются)
    // Лучше не вызывать для воскресений
    private fun week7to6(dayNum: Int): Int {
        val weekNum = (dayNum + startOffset) / 7
        val dayOfWeek = (dayNum + startOffset) % 7
        return weekNum * 6 + dayOfWeek - startOffset
    }

    // обратное преобразование
    private fun week6to7(dayNum: Int): Int {
        val weekNum = (dayNum + startOffset) / 6
        val dayOfWeek = (dayNum + startOffset) % 6
        return weekNum * 7 + dayOfWeek - startOffset
    }

    fun weekInfo(weekNum: Int): WeekInfo {

        val weekCalendar =
            (startMonday.clone() as Calendar).apply { add(Calendar.DATE, weekNum * 7 - 1) }

        val days = (1..6).map {
            weekCalendar.add(Calendar.DATE, 1)
            val dayNum = dateToDayNum(weekCalendar)
            DayInfo(
                dayOfWeek = DayOfWeek.from(weekCalendar),
                dayOfMonth = weekCalendar.get(Calendar.DAY_OF_MONTH),
                dayNum = dayNum,
                isNotActive = dayNum !in 0 until daysCount,
                isCurrent = dayNum == currentNumDay,
            )
        }

        return WeekInfo(
            weekNum,
            type = if (isNumeratorFirst) {
                if (weekNum % 2 == 0) WeekType.NUMERATOR else WeekType.DENOMINATOR
            } else {
                if (weekNum % 2 == 0) WeekType.DENOMINATOR else WeekType.NUMERATOR
            },
            days = days
        )
    }

}

data class DayInfo(
    val dayOfWeek: DayOfWeek,
    val dayOfMonth: Int,
    // Иконка дня неактивна, если этот день не является днем семестра (например, семестр редко
    // начинается с понедельника)
    val isNotActive: Boolean,
    // true, если этот день сегодня
    val isCurrent: Boolean,
    // Это нужно для клика
    val dayNum: Int,
)

data class WeekInfo(
    // Начинается с нуля, но на экране будет на 1 больше
    val weekNum: Int,
    val type: WeekType,
    val days: List<DayInfo>,
)