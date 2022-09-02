package ru.crazy_what.bmstu_shedule

import org.junit.Test
import ru.crazy_what.bmstu_shedule.data.getMonday
import java.util.*

// Тесты всего, что связано с логикой шедулера

class SchedulerUnitTest {

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