package ru.crazy_what.bmstu_shedule.ui.schedule_viewer.model

import ru.crazy_what.bmstu_shedule.domain.model.Lesson

data class LessonWithInfo(
    val lesson: Lesson,
    val timeProgress: Float? = null,
    val messageFromAbove: String? = null,
    val messageBelow: String? = null,
)
