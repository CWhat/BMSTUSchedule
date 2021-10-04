package ru.crazy_what.bmstu_shedule.ui.tabs.search

sealed class SearchState {
    object Loading : SearchState()
    class Error(val message: String) : SearchState()
    class ShowFaculties(val groups: List<String>) : SearchState()
    class ShowChairs(val groups: List<String>, val faculty: String) : SearchState()
    class ShowGroups(val groups: List<String>, val chair: String) : SearchState()
    class ShowGroupSchedule(val groupName: String) : SearchState()
    // TODO
}
