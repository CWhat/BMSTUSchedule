package ru.crazy_what.bmstu_shedule.domain.repository

import ru.crazy_what.bmstu_shedule.common.Group
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.model.SimpleGroupSchedule

interface GroupScheduleRepository {

    suspend fun insertSchedule(schedule: SimpleGroupSchedule)

    suspend fun deleteSchedule(groupName: String)

    suspend fun searchScheduleByGroupName(groupName: String): GroupSchedule

    suspend fun searchGroups(text: String): List<String>

    suspend fun getAllGroupsName(): List<Group>
}