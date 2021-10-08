package ru.crazy_what.bmstu_shedule.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.crazy_what.bmstu_shedule.data.db.models.LessonDb

@Database(entities = [LessonDb::class], version = 1)
abstract class LessonsDatabase : RoomDatabase() {

    abstract fun lessonsDao(): LessonsDao

}