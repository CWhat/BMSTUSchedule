package ru.crazy_what.bmstu_shedule.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.crazy_what.bmstu_shedule.date.Date
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.LessonsListState

// TODO сделать и перейти на него
interface GroupScheduler : BaseScheduler {
    // TODO научиться определять текущую или ближайшую следующую пару
    // Ближайшая пара находится либо в текущем дне (currentDay), либо в следующем

    // LessonsListState лучше заменить на что-то другое, что не будет в папке с ui
    fun getSchedule(dayNum: Int): Flow<LessonsListState>
}