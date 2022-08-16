package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule

sealed class ScheduleViewerState {
    object Loading : ScheduleViewerState()
    class Schedule(val scheduler: GroupSchedule) : ScheduleViewerState()
    class Error(val message: String) : ScheduleViewerState()
}