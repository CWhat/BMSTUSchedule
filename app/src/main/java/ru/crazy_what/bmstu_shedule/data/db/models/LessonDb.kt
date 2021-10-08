package ru.crazy_what.bmstu_shedule.data.db.models

import androidx.room.*
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.common.Group
import ru.crazy_what.bmstu_shedule.data.db.converters.DayOfWeakConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.SimpleListConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.TimeConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.WeekTypeConverter
import ru.crazy_what.bmstu_shedule.domain.model.LessonInfo

@Entity(
    tableName = Constants.LESSONS_DATABASE,
    //primaryKeys = ["weekType", "dayOfWeek", "beginTime", "endTime", Constants.GROUPS_COLUMN_NAME]
)
@TypeConverters(
    TimeConverter::class,
    WeekTypeConverter::class,
    DayOfWeakConverter::class,
    SimpleListConverter::class,
)
data class LessonDb(

    @Embedded
    val info: LessonInfo,

    @ColumnInfo(name = Constants.GROUPS_COLUMN_NAME)
    val groups: List<Group>,
    val teachers: List<String>,

    @PrimaryKey(autoGenerate = true)
    // возможно, можно обойтись и обычным val, но я не уверен
    var id: Id? = null,

    /*@Embedded
    val baseLesson: BaseLessonDb,*/
)
