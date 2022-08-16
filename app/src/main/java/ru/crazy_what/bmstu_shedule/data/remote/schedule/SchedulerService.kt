package ru.crazy_what.bmstu_shedule.data.remote.schedule

import ru.crazy_what.bmstu_shedule.domain.model.SimpleGroupSchedule

interface SchedulerService {

    // Возвращает список групп
    suspend fun groups(): ResponseResult<List<String>>

    // Возвращает расписание по имени группы
    suspend fun schedule(group: String): ResponseResult<SimpleGroupSchedule>

}