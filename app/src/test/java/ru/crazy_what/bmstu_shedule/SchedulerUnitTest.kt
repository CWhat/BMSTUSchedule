package ru.crazy_what.bmstu_shedule

import org.junit.Test
import ru.crazy_what.bmstu_shedule.data.shedule.getMonday
import ru.crazy_what.bmstu_shedule.data.shedule.getWeek
import ru.crazy_what.bmstu_shedule.data.shedule.getWeekOfSchoolYear
import java.util.*

// Тесты всего, что связано с логикой шедулера

class SchedulerUnitTest {

    @Test
    fun getWeek_isCorrect() {
        assert(getWeek(GregorianCalendar(2021, Calendar.SEPTEMBER, 1)) == 1)
        assert(getWeek(GregorianCalendar(2021, Calendar.SEPTEMBER, 2)) == 1)
        assert(getWeek(GregorianCalendar(2021, Calendar.SEPTEMBER, 5)) == 1)
        assert(getWeek(GregorianCalendar(2021, Calendar.SEPTEMBER, 7)) == 2)

        // 2 семестр начинается на 24 неделе 8 февраля
        assert(getWeek(GregorianCalendar(2021, Calendar.FEBRUARY, 8)) == 1)
    }

    @Test
    fun getWeekOfSchoolYear_isCorrect() {
        assert(getWeekOfSchoolYear(GregorianCalendar(2021, Calendar.SEPTEMBER, 1)) == 1)
        assert(getWeekOfSchoolYear(GregorianCalendar(2021, Calendar.SEPTEMBER, 2)) == 1)
        assert(getWeekOfSchoolYear(GregorianCalendar(2021, Calendar.SEPTEMBER, 5)) == 1)
        assert(getWeekOfSchoolYear(GregorianCalendar(2021, Calendar.SEPTEMBER, 7)) == 2)

        // 2 семестр начинается на 24 неделе 8 февраля
        assert(getWeekOfSchoolYear(GregorianCalendar(2021, Calendar.FEBRUARY, 8)) == 24)
    }

    @Test
    fun getMonday_isCorrect() {
        assert(
            getMonday(
                // Эта дата является понедельником
                GregorianCalendar(
                    2021,
                    Calendar.AUGUST,
                    30
                )
            ).get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
        )
    }
}