package ru.crazy_what.bmstu_shedule

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.crazy_what.bmstu_shedule.domain.repository.Scheduler
import ru.crazy_what.bmstu_shedule.data.schedule.SchedulerImpl
import ru.crazy_what.bmstu_shedule.data.remote.schedule.ResponseResult
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerService

// TODO убрать это
class MainViewModel : ViewModel() {

    // TODO добавлять из графа зависимостей
    private val schedulerService = SchedulerService()

//    val groupsFlow =
//        MutableStateFlow<ResponseResult<List<String>>>(ResponseResult.error("Данные еще не загружены"))
//
//    val scheduleFlow =
//        MutableStateFlow<ResponseResult<Scheduler>>(ResponseResult.error("Данные еще не загружены"))

    val groupsFlow = MutableSharedFlow<ResponseResult<List<String>>>()

    val scheduleFlow = MutableSharedFlow<ResponseResult<Scheduler>>()

    /*@Composable
    fun test() {
        groupsFlow.collectAsState()
    }*/

    suspend fun loadGroups() {
        groupsFlow.emit(schedulerService.groups())
    }

    suspend fun loadSchedule(group: String) {
        val respResult = schedulerService.schedule(group)
        if (respResult is ResponseResult.Error) scheduleFlow.emit(ResponseResult.error(respResult.message))
        else scheduleFlow.emit(
            ResponseResult.success(
                SchedulerImpl((respResult as ResponseResult.Success).data)
            )
        )
    }

}