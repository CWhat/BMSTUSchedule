package ru.crazy_what.bmstu_shedule.ui.screen.load_schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.domain.use_case.CheckScheduleIsLoaded
import ru.crazy_what.bmstu_shedule.domain.use_case.InitGroupSchedule
import javax.inject.Inject

@HiltViewModel
class LoadScheduleViewModel @Inject constructor(
    private val checkScheduleIsLoaded: CheckScheduleIsLoaded,
    private val initGroupSchedule: InitGroupSchedule,
) : ViewModel() {

    private val _state = mutableStateOf<LoadScheduleState>(LoadScheduleState.Init)
    val state: State<LoadScheduleState> = _state

    init {
        checkLoaded()
    }

    private fun checkLoaded() {
        viewModelScope.launch {
            checkScheduleIsLoaded().collectLatest { checkResource ->
                when (checkResource) {
                    is Resource.Success -> {
                        if (checkResource.data == true)
                            _state.value = LoadScheduleState.Ready
                        else
                            loadSchedule()
                    }
                    is Resource.Error -> _state.value =
                        LoadScheduleState.Error(checkResource.message ?: "null")
                    is Resource.Loading -> { /* TODO если ничего не делать, то можно впасть в бесконечный цикл */
                    }
                }
            }
        }
    }

    private suspend fun loadSchedule() {

        //initGroupSchedule().onEach { state ->
        initGroupSchedule().collect { state ->
            _state.value = when (state) {
                is InitGroupSchedule.LoadState.Init -> LoadScheduleState.Init
                is InitGroupSchedule.LoadState.InProgress -> LoadScheduleState.InProgress(
                    state.current,
                    state.total,
                )
                is InitGroupSchedule.LoadState.Ready -> LoadScheduleState.Ready
                is InitGroupSchedule.LoadState.Error -> LoadScheduleState.Error(state.message)
            }

        }//.launchIn(viewModelScope)
    }

}