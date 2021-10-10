package ru.crazy_what.bmstu_shedule.data.db

import ru.crazy_what.bmstu_shedule.common.Group
import ru.crazy_what.bmstu_shedule.common.normalizeGroupName
import ru.crazy_what.bmstu_shedule.data.db.converters.SimpleListConverter
import ru.crazy_what.bmstu_shedule.data.db.models.LessonDb
import ru.crazy_what.bmstu_shedule.data.db.models.toGroupLesson
import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import javax.inject.Inject

class GroupScheduleRepositoryImpl @Inject constructor(
    private val lessonsDao: LessonsDao,
) : GroupScheduleRepository {

    // TODO проверить
    override suspend fun insertSchedule(schedule: GroupSchedule) {
        val oldLessons = lessonsDao.searchScheduleByGroupName(schedule.groupName)

        // находим изменения в расписании
        val oldGroupLessons = oldLessons.map { it.toGroupLesson() }
        val newGroupLessons = schedule.lessons.values.flatten()
        // удаленные записи
        val deletedGroupLessons = oldGroupLessons - newGroupLessons
        // новые записи
        val addedGroupLesson = newGroupLessons - oldGroupLessons

        // удаляем то, что нужно удалить
        for (lesson in oldLessons) {
            if (deletedGroupLessons.contains(lesson.toGroupLesson()))
                removeGroupFromRecord(lesson, schedule.groupName)
        }

        // добавляем то, что нужно добавить
        // проблема состоит в том, что нужно проверять на то, что эту новую запись
        // можно объединить с уже существующей записью
        for (lesson in addedGroupLesson) {
            val similar = lessonsDao.findSimilar(
                weekType = lesson.info.weekType,
                dayOfWeek = lesson.info.dayOfWeek,
                beginTime = lesson.info.beginTime,
                endTime = lesson.info.endTime,
                type = lesson.info.type,
                name = lesson.info.name,
                cabinet = lesson.info.cabinet,
            )
            var added = false
            for (similarLessonDb in similar) {
                if (similarLessonDb.teachers.size == lesson.teachers.size &&
                    similarLessonDb.teachers.containsAll(lesson.teachers)
                ) {
                    addGroupToRecord(similarLessonDb, schedule.groupName)
                    added = true
                    break
                }
            }

            if (!added) {
                lessonsDao.insertLessonEntity(
                    LessonDb(
                        info = lesson.info,
                        groups = listOf(normalizeGroupName(schedule.groupName)),
                        teachers = lesson.teachers,
                    )
                )
            }
        }
    }

    // TODO проверить
    override suspend fun deleteSchedule(groupName: String) {
        val lessons = lessonsDao.searchScheduleByGroupName(groupName)
        for (lesson in lessons) {
            removeGroupFromRecord(lesson, groupName)
        }
    }

    private suspend fun removeGroupFromRecord(lessonDb: LessonDb, groupName: String) {
        val normalGroupName = normalizeGroupName(groupName)

        if (!lessonDb.groups.contains(normalGroupName)) return

        if (lessonDb.groups.size > 1) {
            // заменяем на то же самое, только без этой группы
            lessonsDao.insertLessonEntity(
                LessonDb(
                    id = lessonDb.id,
                    info = lessonDb.info,
                    groups = lessonDb.groups.filter {
                        !it.contains(normalGroupName)
                    },
                    teachers = lessonDb.teachers,
                )
            )
        } else lessonsDao.deleteLessonEntity(lessonDb)
    }

    private suspend fun addGroupToRecord(lessonDb: LessonDb, groupName: String) {
        val normalGroupName = normalizeGroupName(groupName)

        if (lessonDb.groups.contains(normalGroupName)) return

        lessonsDao.insertLessonEntity(
            LessonDb(
                info = lessonDb.info,
                id = lessonDb.id,
                teachers = lessonDb.teachers,
                groups = lessonDb.groups + groupName,
            )
        )
    }

    // TODO проверить
    override suspend fun searchScheduleByGroupName(groupName: String): GroupSchedule {
        val lessons = lessonsDao.searchScheduleByGroupName(groupName)

        val lessonsMap = mutableMapOf<Pair<WeekType, DayOfWeek>, List<GroupLesson>>()

        for (lesson in lessons) {
            val time = lesson.info.weekType to lesson.info.dayOfWeek
            val groupLesson = lesson.toGroupLesson()

            if (lessonsMap.containsKey(time)) {
                val newLessonList = lessonsMap[time]!!.toMutableList()
                newLessonList.add(groupLesson)
                lessonsMap[time] = newLessonList
            } else {
                lessonsMap[time] = listOf(groupLesson)
            }
        }

        return GroupSchedule(
            groupName = groupName,
            lessons = lessonsMap,
        )
    }

    // TODO проверить
    override suspend fun searchGroups(text: String): List<String> {
        return lessonsDao.searchGroupsByName(normalizeGroupName(text))
    }

    override suspend fun getAllGroupsName(): List<Group> {
        val converter = SimpleListConverter()

        val groupsListList = lessonsDao.getAllGroupsName()
        // TODO нужна более хорошая сортировка, что РЛ1-91 шло раньше РЛ1-111
        val groupsNameSet = sortedSetOf<Group>()
        for (groupsList in groupsListList) {
            for (groupName in converter.toGroups(groupsList))
                groupsNameSet.add(groupName)
        }

        return groupsNameSet.toList()
    }
}