package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import ru.crazy_what.bmstu_shedule.domain.model.GroupLessonWithInfo

sealed class GroupLessonsListState {
    object Loading : GroupLessonsListState()
    class Error(val message: String) : GroupLessonsListState()
    class Lessons(val lessonsWithInfo: List<GroupLessonWithInfo>) : GroupLessonsListState()
}