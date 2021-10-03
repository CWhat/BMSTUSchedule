package ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BookmarksViewModel : ViewModel() {

    private val _state = mutableStateOf<BookmarksState>(BookmarksState.Loading)
    val state: State<BookmarksState> = _state

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        // TODO Загружаем список групп из закладок и меняем state
    }

}