package ru.crazy_what.bmstu_shedule

import org.junit.Test
import ru.crazy_what.bmstu_shedule.data.shedule.getWeek
import java.util.*

// Тесты всего, что связано с логикой шедулера

class SchedulerUnitTest {

    @Test
    fun getWeek_isCorrect() {
        assert(getWeek(GregorianCalendar(2021, Calendar.SEPTEMBER, 1)) == 1)
        assert(getWeek(GregorianCalendar(2021, Calendar.SEPTEMBER, 2)) == 1)
        assert(getWeek(GregorianCalendar(2021, Calendar.SEPTEMBER, 5)) == 1)
        assert(getWeek(GregorianCalendar(2021, Calendar.SEPTEMBER, 7)) == 2)
    }
}