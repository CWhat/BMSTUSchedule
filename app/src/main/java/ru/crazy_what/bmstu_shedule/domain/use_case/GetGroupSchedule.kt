package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.data.remote.schedule.ResponseResult
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerService
import ru.crazy_what.bmstu_shedule.data.schedule.SchedulerImpl
import ru.crazy_what.bmstu_shedule.domain.repository.Scheduler
import javax.inject.Inject

// TODO добавить dependency injection
class GetGroupSchedule @Inject constructor(private val service: SchedulerService) {

    operator fun invoke(group: String): Flow<Resource<Scheduler>> = flow {
        emit(Resource.Loading())
        when (val scheduleResult = service.schedule(group)) {
            is ResponseResult.Error -> emit(Resource.Error(scheduleResult.message))
            // TODO SchedulerImpl надо брать из другого места (откуда-то из data)
            is ResponseResult.Success -> emit(Resource.Success(SchedulerImpl(scheduleResult.data) as Scheduler))
        }
    }
}