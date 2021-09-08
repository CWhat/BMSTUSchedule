package ru.crazy_what.bmstu_shedule.data.shedule.services

import ru.crazy_what.bmstu_shedule.data.shedule.BiweeklySchedule

interface SchedulerService {

    // Возвращает список групп
    suspend fun groups(): ResponseResult<List<String>>

    // Возвращает расписание по имени группы
    // TODO лучше отдавать BiweeklySchedule
    suspend fun schedule(group: String): ResponseResult<BiweeklySchedule>

}

fun SchedulerService() = SchedulerServiceImpl()