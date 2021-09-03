package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.Lesson

// TODO сделать тестовую реализацию
// TODO как сделать редактор расписания
// Тут отсчет идет с единицы, мне показалось это логичным
interface Scheduler {

    //fun updateTime(date: Calendar)
    //fun updateTime() = updateTime(Calendar.getInstance())

    // TODO научиться определять текущую или ближайшую следующую пару
    // Ближайшая пара находится либо в текущем дне (currentDay), либо в следующем

    val numberOfStudyDaysInSemester: Int
    val numberOfWeeksInSemester: Int

    // null, если сейчас другой семестр
    // возвращаются именно номера относительно первой недели или первого дня этого семестра
    //val currentWeek: WeekInfo?
    val currentWeek: Int?
    val currentDay: Int?

    fun studyDay(studyDayNum: Int): List<Lesson>
    // TODO возможно надо возвращать WeekInfo?
    fun studyWeekInfo(weekNum: Int): WeekInfo
    // TODO возможно надо возвращать StudyDayInfo?
    fun studyDayInfo(studyDayNum: Int): StudyDayInfo
    fun whichWeekDoesDayBelong(day: StudyDayInfo): Int

    // TODO удалить
    fun studyWeek(weekNum: Int): List<StudyDayInfo>

}