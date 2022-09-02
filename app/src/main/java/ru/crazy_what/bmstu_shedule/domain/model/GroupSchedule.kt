package ru.crazy_what.bmstu_shedule.domain.model

import ru.crazy_what.bmstu_shedule.data.getMonday
import ru.crazy_what.bmstu_shedule.data.getTimeBetween
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Time
import ru.crazy_what.bmstu_shedule.date.WeekType
import java.util.*

// По сравнению с SimpleGroupSchedule, уже полноценное расписание
interface GroupSchedule {

    val groupName: String

    val start: Calendar

    val end: Calendar

    var currentTime: Calendar

    val isNumeratorFirst: Boolean

    // Реализация может кидать ошибку, если дата некорретна или ошибка сети (если занятия
    // динамически грузяться из сети)
    suspend fun getSchedule(date: Calendar): List<GroupLessonWithInfo>

}

// TODO написать тесты
class GroupScheduleImpl(
    override val start: Calendar,
    override val end: Calendar,
    currentTime: Calendar,
    private val schedule: SimpleGroupSchedule,
    override val isNumeratorFirst: Boolean = true,
) : GroupSchedule {

    override var currentTime: Calendar = currentTime
        set(value) {
            field = value
            setMessage()
        }

    override val groupName: String
        get() = schedule.groupName

    // При установке currentTime нужно:
    // 1. Получаем расписание на день, соответствующий currentTime
    // 2. Получаем минуты и секунды из currentTime
    // 3. Есть четыре варианта: текущая дата находится
    //    1) перед всеми занятиями текущего дня -> показываем сколько времени до первого занятия
    //    2) во время одного из занятий -> показываем сколько осталось
    //    3) между занятиями -> показываем сколько времени осталось до следующего занятия
    //    4) после всех занятий -> смотрим следующий день
    // 4. Если на шаге 3 мы не нашли предмет, то смотрим первый предмет следующего дня (если
    // предметов в следующий день нет, то никакого сообщения не будет)
    private lateinit var message: LessonMessage

    init {
        setMessage()
    }

    // Есть два вида сообщения:
    // 1. "через Xч Yмин"
    // 2. "осталось Xч Yмин" и прогресс в виде Float
    // TODO как хранить сообщение? Оно же должно быть еще в идеале локализовано
    // Пока буду хранить так
    private data class LessonMessage(
        val year: Int,
        val month: Int,
        val day: Int,
        // Предполагается, что у всех занятий разное время начала
        val beginTime: Time,
        val timeProgress: Float? = null,
        val messageFromAbove: String? = null,
        val messageBelow: String? = null,
    )

    private fun getDayType(date: Calendar): Pair<WeekType, DayOfWeek> {
        val dayNum = offset + getTimeBetween(start, date)
        val weekNum = dayNum / 7

        val weekType = if (isNumeratorFirst) {
            if (weekNum % 2 == 0) WeekType.NUMERATOR else WeekType.DENOMINATOR
        } else {
            if (weekNum % 2 == 0) WeekType.DENOMINATOR else WeekType.NUMERATOR
        }

        val dayOfWeek = DayOfWeek.from(date)

        return Pair(weekType, dayOfWeek)
    }

    private fun nextDay(weekType: WeekType, dayOfWeek: DayOfWeek): Pair<WeekType, DayOfWeek> {
        // Написал еще и воскресенье, чтобы не усложнять

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
            return Pair(
                if (weekType == WeekType.NUMERATOR) WeekType.DENOMINATOR else WeekType.NUMERATOR,
                DayOfWeek.MONDAY,
            )

        return Pair(weekType, when (dayOfWeek) {
            DayOfWeek.MONDAY -> DayOfWeek.TUESDAY
            DayOfWeek.TUESDAY -> DayOfWeek.WEDNESDAY
            DayOfWeek.WEDNESDAY -> DayOfWeek.THURSDAY
            DayOfWeek.THURSDAY -> DayOfWeek.FRIDAY
            // Под else понимается DayOfWeek.FRIDAY, но студия ругается, если написать
            // DayOfWeek.FRIDAY, хотя DayOfWeek.SATURDAY и DayOfWeek.SUNDAY рассмотрены выше
            else -> DayOfWeek.SATURDAY
        })
    }

    private fun Time.toScheduleTime(): String = (if (this.hours != 0) "${this.hours}ч " else "") +
            (if (this.minutes != 0) "${this.minutes}мин" else "")

    private fun setMessage() {
        val year = currentTime.get(Calendar.YEAR)
        val month = currentTime.get(Calendar.MONTH)
        val day = currentTime.get(Calendar.DAY_OF_MONTH)

        val (weekType, dayOfWeek) = getDayType(currentTime)
        val current = Time(currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE))

        val lessons = schedule[weekType, dayOfWeek].sortedBy {
            val beginTime = it.begin
            return@sortedBy beginTime.hours * 60 + beginTime.minutes
        }
        for (i in lessons.indices) {
            val lesson = lessons[i]
            if (current < lesson.begin) {
                val diff = lesson.begin - current
                message = LessonMessage(
                    year, month, day, lesson.begin,
                    messageFromAbove = "через " + diff.toScheduleTime(),
                )
                return
            } else if (lesson.begin <= current && current < lesson.end) {
                // делаем сообщение, сколько осталось
                val left = lesson.end - current
                message = LessonMessage(
                    year, month, day, lesson.begin,
                    timeProgress = 1F - left.absMinutes.toFloat() / (lesson.end - lesson.begin).absMinutes,
                    messageBelow = "осталось " + left.toScheduleTime(),
                )
                return
            }
        }

        val (nextWeekType, nextDayOfWeek) = nextDay(weekType, dayOfWeek)
        val nextLessons = schedule[nextWeekType, nextDayOfWeek]

        message = if (nextLessons.isNotEmpty()) {
            // делаем сообщение для этого первого занятия
            val firstLesson =
                nextLessons.minByOrNull { it.begin.hours * 60 + it.begin.minutes }!!
            val nextDay = (currentTime.clone() as Calendar).apply { this.add(Calendar.DATE, 1) }

            LessonMessage(
                nextDay.get(Calendar.YEAR),
                nextDay.get(Calendar.MONTH),
                nextDay.get(Calendar.DAY_OF_MONTH),
                firstLesson.begin,
                messageFromAbove = "через " + ((Time(24,
                    0) - current) + firstLesson.begin).toScheduleTime()
            )
        } else NoneMessage
    }

    private val offset: Int = getTimeBetween(getMonday(start), start)

    override suspend fun getSchedule(date: Calendar): List<GroupLessonWithInfo> {
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val day = date.get(Calendar.DAY_OF_MONTH)

        val (weekType, dayOfWeek) = getDayType(date)

        return schedule[weekType, dayOfWeek].map {
            if (message.year != year || message.month != month || message.day != day) GroupLessonWithInfo(
                it)
            else if (it.begin != message.beginTime) GroupLessonWithInfo(it)
            else GroupLessonWithInfo(
                it,
                message.timeProgress,
                messageFromAbove = message.messageFromAbove,
                messageBelow = message.messageBelow
            )
        }
    }

    companion object {
        private val NoneMessage = LessonMessage(0, 0, 0, Time(0, 0), null, null, null)
    }

}
