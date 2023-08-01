package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer

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
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.ScheduleViewerState
import javax.inject.Inject

@HiltViewModel
class ScheduleScreenViewModel @Inject constructor(
    private val getGroupSchedule: GetGroupSchedule,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _isBookmark = mutableStateOf(false)
    val isBookmarks: State<Boolean> = _isBookmark

    // TODO реализовать нормальный вывод
    private val _groupName = mutableStateOf("ФН2-32Б")
    val groupName: State<String> = _groupName

    // TODO иногда обновлять время у groupScheduler
    private val _state = mutableStateOf<ScheduleViewerState>(ScheduleViewerState.Loading)
    val state: State<ScheduleViewerState> = _state

    init {
        // TODO передавать не только uuid, но и имя группы (тогда можно сразу устанавливать группу, не дожидаясь загрузки расписания)
        savedStateHandle.get<String>(Constants.PARAM_GROUP_UUID)
            ?.let { groupUuid -> getSchedule(groupUuid) }
    }

    // TODO стоит переименовать
    fun addBookmark() {
        // TODO добавляем в закладки
        _isBookmark.value = !_isBookmark.value
    }

    private fun getSchedule(group: String) {
        getGroupSchedule(group).onEach { result ->
            when (result) {
                is Resource.Success -> result.data?.let { schedule ->
                    _state.value = ScheduleViewerState.Schedule(schedule)
                    _groupName.value = schedule.groupName
                }
                is Resource.Error -> result.message?.let {
                    _state.value = ScheduleViewerState.Error(it)
                }
                is Resource.Loading -> _state.value = ScheduleViewerState.Loading
            }
        }.launchIn(viewModelScope)

    }
}