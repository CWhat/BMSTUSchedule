package ru.crazy_what.bmstu_shedule.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.common.Group
import ru.crazy_what.bmstu_shedule.data.db.converters.DayOfWeakConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.SimpleListConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.TimeConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.WeekTypeConverter
import ru.crazy_what.bmstu_shedule.date.WeekType
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Time

@Entity(
    tableName = Constants.LESSONS_DATABASE,
)
@TypeConverters(
    TimeConverter::class,
    WeekTypeConverter::class,
    DayOfWeakConverter::class,
    SimpleListConverter::class,
)
data class LessonDb(

    val weekType: WeekType,
    val dayOfWeek: DayOfWeek,
    val beginTime: Time,
    val endTime: Time,
    val type: String,
    val name: String,
    val cabinet: String,

    @ColumnInfo(name = Constants.GROUPS_COLUMN_NAME)
    val groups: List<Group>,
    val teachers: List<String>,

    @PrimaryKey(autoGenerate = true)
    // возможно, можно обойтись и обычным val, но я не уверен
    var id: Id? = null,
)