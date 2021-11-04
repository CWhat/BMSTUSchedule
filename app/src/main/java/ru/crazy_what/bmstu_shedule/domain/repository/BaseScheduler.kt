package ru.crazy_what.bmstu_shedule.domain.repository

import ru.crazy_what.bmstu_shedule.date.Date

// основные возможности менеджера работы с расписанием группы, преподавателя или кабинета
interface BaseScheduler {

    val numberOfStudyDaysInSemester: Int

    // с какого дня начать просмотр расписания
    val initDay: Int
    val currentDay: Int?

    fun getDate(dayNum: Int): Date
    fun weekInfo(weekNum: Int): String
}