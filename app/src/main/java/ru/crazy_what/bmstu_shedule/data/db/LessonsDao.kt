package ru.crazy_what.bmstu_shedule.data.db

import androidx.room.*
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.db.models.Id
import ru.crazy_what.bmstu_shedule.data.db.models.LessonDb

@Dao
interface LessonsDao {

    @Delete
    suspend fun deleteLessonEntity(model: LessonDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessonEntity(model: LessonDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLessonEntity(lesson: LessonDb): Id
    //@Query("INSERT INTO ${Constants.LESSONS_DATABASE} () VALUES ()")
    //suspend fun addLessonEntity(info: LessonInfo, groups: List<Group>, teachers: List<String>): Id

    @Query(
        "SELECT * FROM ${Constants.LESSONS_DATABASE} " +
                "WHERE ${Constants.GROUPS_COLUMN_NAME} LIKE '%' || :groupName || '%'"
    )
    suspend fun searchScheduleByGroupName(groupName: String): List<LessonDb>

    @Query(
        "SELECT DISTINCT ${Constants.GROUPS_COLUMN_NAME} " +
                "FROM ${Constants.LESSONS_DATABASE} " +
                "WHERE ${Constants.GROUPS_COLUMN_NAME} LIKE :groupName || '%'"
    )
    suspend fun searchGroupsByName(groupName: String): List<String>

}