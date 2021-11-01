package ru.crazy_what.bmstu_shedule.data.db

import androidx.room.*
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.db.converters.DayOfWeakConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.SimpleListConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.TimeConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.WeekTypeConverter
import ru.crazy_what.bmstu_shedule.data.db.models.Id
import ru.crazy_what.bmstu_shedule.data.db.models.LessonDb
import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Time

@Dao
interface LessonsDao {

    @Delete
    suspend fun deleteLessonEntity(model: LessonDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonEntity(model: LessonDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLessonEntity(lesson: LessonDb): Id

    // TODO надо усложнить, скорее всего, работает криво
    @Query(
        "SELECT * FROM ${Constants.LESSONS_DATABASE} " +
                "WHERE ${Constants.GROUPS_COLUMN_NAME} LIKE '%' || :groupName || '%'"
    )
    suspend fun searchScheduleByGroupName(groupName: String): List<LessonDb>

    // TODO скорее всего работает не так, как надо
    @Query(
        "SELECT DISTINCT ${Constants.GROUPS_COLUMN_NAME} " +
                "FROM ${Constants.LESSONS_DATABASE} " +
                "WHERE ${Constants.GROUPS_COLUMN_NAME} LIKE :groupName || '%'"
    )
    suspend fun searchGroupsByName(groupName: String): List<String>

    @TypeConverters(
        TimeConverter::class,
        WeekTypeConverter::class,
        DayOfWeakConverter::class,
    )
    @Query(
        "SELECT * FROM ${Constants.LESSONS_DATABASE} WHERE " +
                "weekType = :weekType AND " +
                "dayOfWeek = :dayOfWeek AND " +
                "beginTime = :beginTime AND " +
                "endTime = :endTime AND " +
                "type = :type AND " +
                "name = :name AND " +
                "cabinet = :cabinet"
    )
    suspend fun findSimilar(
        weekType: WeekType,
        dayOfWeek: DayOfWeek,
        beginTime: Time,
        endTime: Time,
        type: String,
        name: String,
        cabinet: String,
    ): List<LessonDb>

    @TypeConverters(
        SimpleListConverter::class,
    )
    @Query("SELECT DISTINCT ${Constants.GROUPS_COLUMN_NAME} FROM ${Constants.LESSONS_DATABASE}")
    // TODO так почему-то не получается
    //suspend fun getAllGroupsName(): List<List<String>>
    suspend fun getAllGroupsName(): List<String>

}