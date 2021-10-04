package ru.crazy_what.bmstu_shedule.ui.schedule_viewer

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.domain.use_case.GetGroupSchedule
import javax.inject.Inject

@HiltViewModel
class ScheduleViewerViewModel @Inject constructor(
    private val getGroupSchedule: GetGroupSchedule,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _groupName = mutableStateOf("ФН2-32Б")
    val groupName: State<String> = _groupName

    private val _state = mutableStateOf<ScheduleViewerState>(ScheduleViewerState.Loading)
    val state: State<ScheduleViewerState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_GROUP_NAME)
            ?.let { groupName -> getSchedule(groupName) }
    }

    private fun getSchedule(group: String) {
        _groupName.value = group

        getGroupSchedule(group).onEach { result ->
            when (result) {
                is Resource.Success -> result.data?.let {
                    _state.value = ScheduleViewerState.Schedule(result.data)
                }
                is Resource.Error -> result.message?.let {
                    _state.value = ScheduleViewerState.Error(it)
                }
                is Resource.Loading -> _state.value = ScheduleViewerState.Loading
            }
        }.launchIn(viewModelScope)
    }
}