package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import ru.crazy_what.bmstu_shedule.domain.repository.Scheduler

sealed class ScheduleViewerState {
    object Loading : ScheduleViewerState()
    class Schedule(val scheduler: Scheduler) : ScheduleViewerState()
    class Error(val message: String) : ScheduleViewerState()
}