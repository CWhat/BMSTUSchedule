package ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.data.schedule.StudyDayInfo
import ru.crazy_what.bmstu_shedule.data.schedule.WeekInfo

@ExperimentalPagerApi
@Composable
fun NewScheduleViewer(
    getLessonsList: (numDay: Int) -> Flow<LessonsListState>,
    // TODO возможно, надо тоже заменить на Flow
    getWeekInfo: (numWeek: Int) -> WeekInfo,
    // TODO возможно, надо тоже заменить на Flow
    getDayInfo: (numDay: Int) -> StudyDayInfo,
    isToday: (numDay: Int) -> Boolean,
    numberOfDays: Int,
    initialWeek: Int,
    initialDay: Int,
    daysInWeek: Int = 6,
    // функция, определяющую какой неделе принадлежит день из аргумента
    isWeek: (numDay: Int) -> Int = { numDay ->
        numDay / daysInWeek
    },
) {
    val numberOfWeeks = numberOfDays / daysInWeek + if (numberOfDays % daysInWeek != 0) 1 else 0
    val weeksState = rememberPagerState(pageCount = numberOfWeeks, initialPage = initialWeek)

    val lessonsState = rememberPagerState(pageCount = numberOfDays, initialPage = initialDay)

    // scope для анимации прокручивания списка
    val scope = rememberCoroutineScope()

    // при изменении lessonsState меняем weeksState
    LaunchedEffect(lessonsState) {
        snapshotFlow { lessonsState.currentPage }.collect { page ->
            scope.launch {
                weeksState.animateScrollToPage(isWeek(page))
            }
        }
    }

    DateLine(
        weeksState = weeksState,
        lessonsState = lessonsState,
        onClick = { dayInfo ->
            scope.launch {
                lessonsState.animateScrollToPage(dayInfo.studyDayNum)
            }
        },
        getWeekInfo = getWeekInfo,
        getDayInfo = getDayInfo,
        isToday = isToday
    )

    // список занятий
    HorizontalPager(state = lessonsState) { page -> LessonsList(getLessonsList(page)) }

}