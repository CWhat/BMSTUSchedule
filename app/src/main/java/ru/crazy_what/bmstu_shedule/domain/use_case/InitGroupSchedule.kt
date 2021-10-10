package ru.crazy_what.bmstu_shedule.domain.use_case

import ru.crazy_what.bmstu_shedule.data.remote.schedule.ResponseResult
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerService
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import javax.inject.Inject

// TODO надо как-то проверять, что расписание было загружено
class InitGroupSchedule @Inject constructor(
    private val groupScheduleRepository: GroupScheduleRepository,
    private val schedulerService: SchedulerService,
) {

    // TODO можно возвращать Flow, который выводит прогресс (например, Pair<Int, Int>)
    suspend operator fun invoke() {
        when (val groupsResponse = schedulerService.groups()) {
            is ResponseResult.Error -> {
                error("При загрузке списка групп произошла ошибка ${groupsResponse.message}")
            }
            is ResponseResult.Success -> {
                val groups = groupsResponse.data

                for (groupName in groups) {
                    when (val groupScheduleResponse = schedulerService.schedule(groupName)) {
                        is ResponseResult.Error -> {
                            error("При загрузке расписания произошла ошибка ${groupScheduleResponse.message}")
                        }
                        is ResponseResult.Success -> {
                            val groupSchedule = groupScheduleResponse.data
                            groupScheduleRepository.insertSchedule(groupSchedule)
                        }
                    }
                }
            }
        }
    }

}