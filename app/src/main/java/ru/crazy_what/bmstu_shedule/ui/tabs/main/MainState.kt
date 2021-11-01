package ru.crazy_what.bmstu_shedule.ui.tabs.main

import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduler

sealed class MainState {
    object Loading : MainState()
    class MainGroup(val groupName: String, val groupScheduler: GroupScheduler) : MainState()
    class Error(val message: String) : MainState()
}
