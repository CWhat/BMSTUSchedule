package ru.crazy_what.bmstu_shedule.data

import androidx.compose.runtime.Composable
import java.util.*

enum class Month {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    companion object {
        fun from(date: Calendar): Month = when (date.get(Calendar.MONTH)) {
            Calendar.JANUARY -> JANUARY
            Calendar.FEBRUARY -> FEBRUARY
            Calendar.MARCH -> MARCH
            Calendar.APRIL -> APRIL
            Calendar.MAY -> MAY
            Calendar.JUNE -> JUNE
            Calendar.JULY -> JULY
            Calendar.AUGUST -> AUGUST
            Calendar.SEPTEMBER -> SEPTEMBER
            Calendar.OCTOBER -> OCTOBER
            Calendar.NOVEMBER -> NOVEMBER
            Calendar.DECEMBER -> DECEMBER
            else -> error("Некорректный месяц")
        }
    }

}

// Подобный трюк нужен для того, чтобы при конвертировании в строку, можно было взять перевод
@Composable
fun Month.toLangString(): String = when (this) {
    // тут можно так stringResource(id = )
    Month.JANUARY -> "Январь"
    Month.FEBRUARY -> "Февраль"
    Month.MARCH -> "Март"
    Month.APRIL -> "Апрель"
    Month.MAY -> "Май"
    Month.JUNE -> "Июнь"
    Month.JULY -> "Июль"
    Month.AUGUST -> "Август"
    Month.SEPTEMBER -> "Сентябрь"
    Month.OCTOBER -> "Октябрь"
    Month.NOVEMBER -> "Ноябрь"
    Month.DECEMBER -> "Декабрь"
}