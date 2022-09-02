package ru.crazy_what.bmstu_shedule.domain.repository

import ru.crazy_what.bmstu_shedule.common.Group
import ru.crazy_what.bmstu_shedule.common.ResponseResult
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule

interface GroupScheduleRepository {

    suspend fun searchScheduleByGroupName(groupName: String): ResponseResult<GroupSchedule>

    suspend fun getAllGroupsName(): ResponseResult<List<Group>>
}