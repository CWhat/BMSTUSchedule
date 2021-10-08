package ru.crazy_what.bmstu_shedule.domain.repository

import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule

interface GroupScheduleRepository {

    suspend fun insertSchedule(schedule: GroupSchedule)

    suspend fun deleteSchedule(groupName: String)

    suspend fun searchScheduleByGroupName(groupName: String): GroupSchedule

    suspend fun searchGroups(text: String): List<String>
}