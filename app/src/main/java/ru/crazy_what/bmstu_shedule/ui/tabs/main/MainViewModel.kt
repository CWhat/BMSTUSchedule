package ru.crazy_what.bmstu_shedule.ui.tabs.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.domain.use_case.GetMainGroup
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMainGroup: GetMainGroup
) : ViewModel() {

    private val _state = mutableStateOf<MainState>(MainState.Loading)
    val state: State<MainState> = _state

    init {
        loadMainGroupName()
    }

    private fun loadMainGroupName() {
        getMainGroup().onEach { result ->
            when (result) {
                is Resource.Success -> result.data?.let { _state.value = MainState.MainGroup(it) }
                is Resource.Loading -> _state.value = MainState.Loading
                is Resource.Error -> result.message?.let { _state.value = MainState.Error(it) }
            }
        }.launchIn(viewModelScope)
    }

}