package ru.crazy_what.bmstu_shedule.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Resource
import ru.crazy_what.bmstu_shedule.data.schedule.GroupSchedulerImpl
import ru.crazy_what.bmstu_shedule.data.schedule.SchedulerImpl
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduler
import ru.crazy_what.bmstu_shedule.domain.repository.Scheduler
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

    operator fun invoke(group: String): Flow<Resource<GroupScheduler>> = flow {
        emit(Resource.Loading())
        // TODO нужно добавить обработку ошибок
        val groupSchedule = groupScheduleRepository.searchScheduleByGroupName(group)
        emit(
            Resource.Success(
                GroupSchedulerImpl(
                    groupName = group,
                    groupSchedule = groupSchedule,
                ) as GroupScheduler
            )
        )
    }
}