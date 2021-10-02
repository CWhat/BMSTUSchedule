package ru.crazy_what.bmstu_shedule.date

import androidx.compose.runtime.Composable
import java.util.*

enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    companion object {
        fun from(date: Calendar) = when (date.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> MONDAY
            Calendar.TUESDAY -> TUESDAY
            Calendar.WEDNESDAY -> WEDNESDAY
            Calendar.THURSDAY -> THURSDAY
            Calendar.FRIDAY -> FRIDAY
            Calendar.SATURDAY -> SATURDAY
            Calendar.SUNDAY -> SUNDAY
            else -> error("Некорректный месяц")
        }
    }

    // Подобный трюк нужен для того, чтобы при конвертировании в строку, можно было взять перевод
    @Composable
    fun toShortString(): String = when (this) {
        // тут можно так stringResource(id = )
        MONDAY -> "ПН"
        TUESDAY -> "ВТ"
        WEDNESDAY -> "СР"
        THURSDAY -> "ЧТ"
        FRIDAY -> "ПТ"
        SATURDAY -> "СБ"
        SUNDAY -> "ВС"
    }

}