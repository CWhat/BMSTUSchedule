package ru.crazy_what.bmstu_shedule.ui.schedule_viewer.model

import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson

data class LessonWithInfo(
    val lesson: GroupLesson,
    val timeProgress: Float? = null,
    val messageFromAbove: String? = null,
    val messageBelow: String? = null,
)
