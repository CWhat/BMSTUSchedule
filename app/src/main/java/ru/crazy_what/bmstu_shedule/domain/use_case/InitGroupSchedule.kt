package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.data.remote.schedule.ResponseResult
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerService
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import javax.inject.Inject

// TODO надо как-то проверять, что расписание было загружено
class InitGroupSchedule @Inject constructor(
    private val groupScheduleRepository: GroupScheduleRepository,
    private val schedulerService: SchedulerService,
) {

    sealed class LoadState {
        object Init : LoadState()
        class InProgress(val current: Int, val total: Int) : LoadState()
        object Ready : LoadState()
        class Error(val message: String) : LoadState()
    }

    operator fun invoke(): Flow<LoadState> = flow {
        emit(LoadState.Init)
        when (val groupsResponse = schedulerService.groups()) {
            is ResponseResult.Error -> {
                emit(LoadState.Error("При загрузке списка групп произошла ошибка ${groupsResponse.message}"))
                return@flow
            }
            is ResponseResult.Success -> {
                val groups = groupsResponse.data
                val total = groups.size

                groups.forEachIndexed { index, groupName ->
                    emit(LoadState.InProgress(index + 1, total))
                    when (val groupScheduleResponse = schedulerService.schedule(groupName)) {
                        is ResponseResult.Error -> {
                            emit(LoadState.Error("При загрузке расписания произошла ошибка ${groupScheduleResponse.message}"))
                            return@flow
                        }
                        is ResponseResult.Success -> {
                            val groupSchedule = groupScheduleResponse.data
                            groupScheduleRepository.insertSchedule(groupSchedule)
                        }
                    }
                }
                emit(LoadState.Ready)
            }
        }
    }

}