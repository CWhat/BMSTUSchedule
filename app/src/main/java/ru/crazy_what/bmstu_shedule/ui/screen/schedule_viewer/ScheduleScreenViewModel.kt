package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer

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
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.ScheduleViewerState
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.LessonsListState
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.model.LessonWithInfo
import javax.inject.Inject

@HiltViewModel
class ScheduleScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _isBookmark = mutableStateOf(false)
    val isBookmarks: State<Boolean> = _isBookmark

    private val _groupName = mutableStateOf("ФН2-32Б")
    val groupName: State<String> = _groupName

    private val _state = mutableStateOf<ScheduleViewerState>(ScheduleViewerState.Loading)
    val state: State<ScheduleViewerState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_GROUP_NAME)
            ?.let { groupName -> _groupName.value = groupName }
    }

    fun addBookmark() {
        // TODO добавляем в закладки
        _isBookmark.value = !_isBookmark.value
    }
}