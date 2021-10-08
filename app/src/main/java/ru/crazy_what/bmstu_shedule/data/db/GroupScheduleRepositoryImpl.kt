package ru.crazy_what.bmstu_shedule.data.db

import ru.crazy_what.bmstu_shedule.common.normalizeGroupName
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import javax.inject.Inject

class GroupScheduleRepositoryImpl @Inject constructor(
    private val lessonsDao: LessonsDao,
) : GroupScheduleRepository {

    // TODO проверить
    override suspend fun insertSchedule(schedule: GroupSchedule) {
        TODO("Not yet implemented")
    }

    // TODO проверить
    override suspend fun deleteSchedule(groupName: String) {
        TODO("Not yet implemented")
    }

    // TODO проверить
    override suspend fun searchScheduleByGroupName(groupName: String): GroupSchedule {
        TODO("Not yet implemented")
    }

    // TODO проверить
    override suspend fun searchGroups(text: String): List<String> {
        return lessonsDao.searchGroupsByName(normalizeGroupName(text))
    }
}