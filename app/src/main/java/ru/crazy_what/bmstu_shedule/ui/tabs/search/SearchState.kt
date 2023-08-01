package ru.crazy_what.bmstu_shedule.ui.tabs.search

import ru.crazy_what.bmstu_shedule.domain.model.Group

sealed class SearchState {
    object Loading : SearchState()
    class Error(val message: String) : SearchState()
    class ShowGroups(val groups: List<Group>) : SearchState()
}
