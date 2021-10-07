package ru.crazy_what.bmstu_shedule.ui.tabs.main

sealed class MainState {
    object Loading : MainState()
    class MainGroup(val groupName: String): MainState()
    class Error(val message: String): MainState()
}
