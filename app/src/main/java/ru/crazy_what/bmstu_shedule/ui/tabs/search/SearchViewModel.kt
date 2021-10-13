package ru.crazy_what.bmstu_shedule.ui.tabs.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.domain.use_case.GetGroups
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getGroups: GetGroups
) : ViewModel() {
    private val _state = mutableStateOf<SearchState>(SearchState.Loading)
    val state: State<SearchState> = _state

    init {
        getGroupsList()
    }

    private fun getGroupsList() {
        getGroups().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = SearchState.Loading
                is Resource.Success -> result.data?.let {
                    _state.value = SearchState.ShowFaculties(it)
                }
                is Resource.Error -> result.message?.let { _state.value = SearchState.Error(it) }
            }

        }.launchIn(viewModelScope)
    }

    fun showChairs(groups: List<String>, faculty: String) {
        _state.value = SearchState.ShowChairs(groups, faculty)
    }

    fun showGroups(groups: List<String>, chair: String) {
        _state.value = SearchState.ShowGroups(groups, chair)
    }
}