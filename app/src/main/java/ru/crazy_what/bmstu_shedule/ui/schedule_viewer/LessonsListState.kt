package ru.crazy_what.bmstu_shedule.ui.schedule_viewer


sealed class LessonsListState {
    object Loading : LessonsListState()
    class Error(val message: String) : LessonsListState()
    class Lessons(val lessonsWithInfo: List<LessonWithInfo>) : LessonsListState()
}
