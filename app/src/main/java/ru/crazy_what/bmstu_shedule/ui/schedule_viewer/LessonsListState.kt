package ru.crazy_what.bmstu_shedule.ui.schedule_viewer

import ru.crazy_what.bmstu_shedule.domain.model.Lesson

sealed class LessonsListState {
    object Loading : LessonsListState()
    class Error(val message: String) : LessonsListState()
    class Lessons(val lessons: List<Lesson>) : LessonsListState()
}
