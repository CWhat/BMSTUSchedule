package ru.crazy_what.bmstu_shedule.ui.screen.load_schedule

sealed class LoadScheduleState {
    object Init : LoadScheduleState()
    class InProgress(val current: Int, val total: Int) : LoadScheduleState()
    object Ready : LoadScheduleState()
    class Error(val message: String) : LoadScheduleState()
}