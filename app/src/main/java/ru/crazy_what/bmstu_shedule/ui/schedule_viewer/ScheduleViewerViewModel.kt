package ru.crazy_what.bmstu_shedule.ui.schedule_viewer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.domain.use_case.GetGroupSchedule
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components.LessonsListState
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.model.LessonWithInfo
import javax.inject.Inject

@HiltViewModel
class ScheduleViewerViewModel @Inject constructor(
    private val getGroupSchedule: GetGroupSchedule,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isBookmark = mutableStateOf(false)
    val isBookmarks: State<Boolean> = _isBookmark

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

        // TODO добавить поиск этой группы в закладках
    }

    fun addBookmark() {
        // TODO добавляем в закладки
        _isBookmark.value = !_isBookmark.value
    }

    fun getLessonsList(numDay: Int): Flow<LessonsListState> = flow {
        emit(LessonsListState.Loading)
        if (_state.value is ScheduleViewerState.Schedule) {
            val lessonsWithInfo =
                (_state.value as ScheduleViewerState.Schedule).scheduler.studyDay(numDay + 1)
                    .map {
                        LessonWithInfo(
                            lesson = it
                        )
                    }
            emit(LessonsListState.Lessons(lessonsWithInfo))
        } else emit(LessonsListState.Error("Произошла непредвиденная ошибка"))
    }
}