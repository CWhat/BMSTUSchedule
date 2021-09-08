package ru.crazy_what.bmstu_shedule

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler
import ru.crazy_what.bmstu_shedule.data.shedule.SchedulerImpl
import ru.crazy_what.bmstu_shedule.data.shedule.services.ResponseResult
import ru.crazy_what.bmstu_shedule.data.shedule.services.SchedulerService

class MainViewModel : ViewModel() {

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