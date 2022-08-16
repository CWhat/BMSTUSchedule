package ru.crazy_what.bmstu_shedule.ui.tabs.main

import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule

sealed class MainState {
    object Loading : MainState()
    class MainGroup(val groupName: String, val groupScheduler: GroupSchedule) : MainState()
    class Error(val message: String) : MainState()
}
