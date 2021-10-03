package ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks

sealed class BookmarksState {
    object Loading : BookmarksState()
    class Bookmarks(val groups: List<String>) : BookmarksState()
    class Error(val message: String) : BookmarksState()
}
