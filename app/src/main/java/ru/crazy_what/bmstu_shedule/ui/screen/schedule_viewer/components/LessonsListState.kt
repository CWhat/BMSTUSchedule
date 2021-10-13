package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.model.LessonWithInfo

sealed class LessonsListState {
    object Loading : LessonsListState()
    class Error(val message: String) : LessonsListState()
    class Lessons(val lessonsWithInfo: List<LessonWithInfo>) : LessonsListState()
}
