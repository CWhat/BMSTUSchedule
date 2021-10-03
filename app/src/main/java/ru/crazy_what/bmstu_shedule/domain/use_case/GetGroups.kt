package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.data.remote.schedule.ResponseResult
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerService

// TODO добавить dependency injection
class GetGroups(private val service: SchedulerService = SchedulerService()) {

    operator fun invoke(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())
        when (val groupsResult = service.groups()) {
            is ResponseResult.Error -> emit(Resource.Error(groupsResult.message))
            is ResponseResult.Success -> emit(Resource.Success(groupsResult.data))
        }
    }

}