package ru.crazy_what.bmstu_shedule.data.shedule

import ru.crazy_what.bmstu_shedule.data.Lesson

// TODO переименовать
// Тут отсчет идет с единицы, мне показалось это логичным
interface Scheduler {

    //fun updateTime(date: Calendar)
    //fun updateTime() = updateTime(Calendar.getInstance())

    val numberOfStudyDaysInSemester: Int
    val numberOfWeeksInSemester: Int

    // null, если сейчас другой семестр
    // возвращаются именно номера относительно первой недели или первого дня этого семестра
    val currentWeek: Int?
    val currentDay: Int?

    fun studyDay(studyDayNum: Int): List<Lesson>
    fun studyWeek(weekNum: Int): List<StudyDayInfo>

}