package ru.crazy_what.bmstu_shedule

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler
import ru.crazy_what.bmstu_shedule.data.shedule.services.ResponseResult
import ru.crazy_what.bmstu_shedule.data.shedule.services.SchedulerService

class MainViewModel : ViewModel() {

    val schedulerService = SchedulerService()

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
        scheduleFlow.emit(schedulerService.schedule(group))
    }

}