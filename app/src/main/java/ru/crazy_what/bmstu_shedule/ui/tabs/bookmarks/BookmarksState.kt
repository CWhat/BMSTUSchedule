package ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks

import ru.crazy_what.bmstu_shedule.domain.model.Group

sealed class BookmarksState {
    object Loading : BookmarksState()
    // TODO добавить преподавателей
    class Bookmarks(val groups: List<Group>) : BookmarksState()
    class Error(val message: String) : BookmarksState()
}
