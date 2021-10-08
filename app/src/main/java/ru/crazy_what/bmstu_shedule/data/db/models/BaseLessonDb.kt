package ru.crazy_what.bmstu_shedule.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.common.Group
import ru.crazy_what.bmstu_shedule.data.db.converters.DayOfWeakConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.SimpleListConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.TimeConverter
import ru.crazy_what.bmstu_shedule.data.db.converters.WeekTypeConverter
import ru.crazy_what.bmstu_shedule.domain.model.LessonInfo

/*@Entity
@TypeConverters(
    TimeConverter::class,
    WeekTypeConverter::class,
    DayOfWeakConverter::class,
    SimpleListConverter::class,
)
data class BaseLessonDb(
    @Embedded
    val info: LessonInfo,

    @ColumnInfo(name = Constants.GROUPS_COLUMN_NAME)
    val groups: List<Group>,
    val teachers: List<String>,
)*/
