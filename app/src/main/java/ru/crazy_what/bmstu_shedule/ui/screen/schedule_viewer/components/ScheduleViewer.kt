package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.schedule.WeekType
import ru.crazy_what.bmstu_shedule.date.Date
import ru.crazy_what.bmstu_shedule.date.DayOfWeek
import ru.crazy_what.bmstu_shedule.date.Month
import ru.crazy_what.bmstu_shedule.date.Time
import ru.crazy_what.bmstu_shedule.domain.model.GroupLesson
import ru.crazy_what.bmstu_shedule.domain.model.LessonInfo
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduler
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.model.LessonWithInfo
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import ru.crazy_what.bmstu_shedule.ui.theme.littleTitleStyle

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(scheduleViewerState: ScheduleViewerState) {
    Column(modifier = Modifier.fillMaxSize()) {
        when (scheduleViewerState) {
            is ScheduleViewerState.Loading -> LoadView()
            is ScheduleViewerState.Schedule ->
                GroupScheduleViewer(groupScheduler = scheduleViewerState.scheduler)
            // TODO добавить красивый диалог с ошибкой
            is ScheduleViewerState.Error -> ErrorMessage(
                text = "При загрузке расписания произошла ошибка: ${scheduleViewerState.message}",
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun BaseScheduleViewer(
    countDay: Int,
    initDay: Int,
    currentDay: Int?,
    weekInfo: (weekNum: Int) -> String,
    date: (dayNum: Int) -> Date,
    daySchedule: (dayNum: Int) -> Flow<LessonsListState>,
) {
    PageTabs(
        modifier = Modifier.fillMaxSize(),
        initElement = initDay,
        countElements = countDay,
        tabsPageInfo = { weekNum: Int ->
            Text(
                text = weekInfo(weekNum),
                textAlign = TextAlign.Center,
                style = littleTitleStyle,
            )
        },
        tabLayout = { dayNum, offset ->
            val date = date(dayNum)
            // TODO переделать с нормальным использованием offset
            val state =
                if (offset == 0F && dayNum == currentDay) DateCircleState.CURRENT
                else if (offset == 1F) DateCircleState.SELECT
                else DateCircleState.NONE

            DateCircle(date = date, state = state)
        },
    ) { dayNum ->
        LessonsList(lessonsListState = daySchedule(dayNum))
    }
}

@ExperimentalPagerApi
@Composable
fun GroupScheduleViewer(groupScheduler: GroupScheduler) {
    BaseScheduleViewer(
        countDay = groupScheduler.numberOfStudyDaysInSemester,
        initDay = groupScheduler.initDay,
        currentDay = groupScheduler.currentDay,
        weekInfo = groupScheduler::weekInfo,
        date = groupScheduler::getDate,
        daySchedule = groupScheduler::getSchedule,
    )
}