package ru.crazy_what.bmstu_shedule.ui.tabs.search

sealed class SearchState {
    object Loading : SearchState()
    class Error(val message: String) : SearchState()
    class ShowGroups(val groups: List<String>) : SearchState()
}
