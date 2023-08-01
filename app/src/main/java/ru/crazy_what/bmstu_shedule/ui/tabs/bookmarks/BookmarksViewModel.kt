package ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.domain.use_case.GetAllBookmarks
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getAllBookmarks: GetAllBookmarks
) : ViewModel() {

    private val _state = mutableStateOf<BookmarksState>(BookmarksState.Loading)
    val state: State<BookmarksState> = _state

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        getAllBookmarks().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = BookmarksState.Loading
                is Resource.Success -> result.data?.let {
                    _state.value = BookmarksState.Bookmarks(it)
                }
                is Resource.Error -> result.message?.let {
                    _state.value = BookmarksState.Error(it)
                }
            }
        }.launchIn(viewModelScope)
    }

}