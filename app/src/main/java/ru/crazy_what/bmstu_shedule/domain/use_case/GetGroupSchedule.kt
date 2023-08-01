package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.common.ResponseResult
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import javax.inject.Inject

// старый вариант без использования базы данных, вдруг пригодится
/*class GetGroupSchedule @Inject constructor(private val service: SchedulerService) {

    operator fun invoke(group: String): Flow<Resource<Scheduler>> = flow {
        emit(Resource.Loading())
        when (val scheduleResult = service.schedule(group)) {
            is ResponseResult.Error -> emit(Resource.Error(scheduleResult.message))
            is ResponseResult.Success -> emit(
                Resource.Success(
                    SchedulerImpl(
                        groupName = group,
                        scheduleResult.data
                    ) as Scheduler
                )
            )
        }
    }
}*/

class GetGroupSchedule @Inject constructor(private val groupScheduleRepository: GroupScheduleRepository) {

    operator fun invoke(uuid: String): Flow<Resource<GroupSchedule>> = flow {
        emit(Resource.Loading())
        when (val groupScheduleResult = groupScheduleRepository.getSchedule(uuid)) {
            is ResponseResult.Success -> emit(Resource.Success(groupScheduleResult.data))
            is ResponseResult.Error -> emit(Resource.Error(groupScheduleResult.message))
        }
    }
}