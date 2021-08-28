package ru.crazy_what.bmstu_shedule.data.shedule.services

import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler

interface SchedulerService {

    // Возвращает список групп
    suspend fun groups(): List<String>

    // Возвращает расписание по имени группы
    suspend fun schedule(group: String): Scheduler

}