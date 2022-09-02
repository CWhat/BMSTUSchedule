package ru.crazy_what.bmstu_shedule.data

import java.util.*

// Здесь находятся базовые функции для работы со временем для расписания

// Возвращает разницу в днях между датами
fun getTimeBetween(leftBound: Calendar, rightBound: Calendar): Int {
    val millisecondsPerDay = (24 * 60 * 60 * 1000).toLong()

    return ((rightBound.timeInMillis - leftBound.timeInMillis) / millisecondsPerDay).toInt()
}

// Возвращает предыдущий понедельник
// TODO написать тесты
fun getMonday(date: Calendar): Calendar {
    val dayOfWeek = Calendar.MONDAY
    val weekday = date.get(Calendar.DAY_OF_WEEK)

    // calculate how much to add
    var days = dayOfWeek - weekday
    // Почему-то эта строчка не нужна
    //if (days < 0) days += 7

    val res = date.clone() as Calendar
    res.add(Calendar.DAY_OF_YEAR, days)
    return res
}