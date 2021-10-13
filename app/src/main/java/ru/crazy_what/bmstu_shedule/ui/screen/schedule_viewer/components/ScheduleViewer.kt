package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.schedule.StudyDayInfo
import ru.crazy_what.bmstu_shedule.data.schedule.WeekInfo
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.ScheduleViewerScreen
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.ScheduleViewerState

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(
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

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(viewModel: ScheduleViewerViewModel = hiltViewModel()) {
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is ScheduleViewerState.Loading -> LoadView()
            // TODO поменять
            is ScheduleViewerState.Schedule ->
                with(state.scheduler) {
                    // TODO добавить добавление в закладки
                    ScheduleViewer(
                        getLessonsList = { numDay -> viewModel.getLessonsList(numDay) },
                        getWeekInfo = { weekNum -> studyWeekInfo(weekNum + 1) },
                        getDayInfo = { dayNum ->
                            val info = studyDayInfo(dayNum)
                            StudyDayInfo(date = info.date, studyDayNum = info.studyDayNum - 1)
                        },
                        isToday = { dayNum -> dayNum + 1 == currentDay },
                        numberOfDays = numberOfStudyDaysInSemester,
                        initialWeek = currentWeek?.minus(1) ?: 0,
                        initialDay = currentDay?.minus(1) ?: 0,
                    )
                }
            // TODO добавить красивый диалог с ошибкой
            is ScheduleViewerState.Error -> ErrorMessage(
                text = "При загрузке расписания произошла ошибка: ${state.message}",
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@ExperimentalPagerApi
fun NavGraphBuilder.addScheduleViewer(defaultGroup: String? = null) {
    composable(
        route = "${Constants.ROUTE_SCHEDULE_VIEWER}/{${Constants.PARAM_GROUP_NAME}}",
        arguments = listOf(navArgument(name = Constants.PARAM_GROUP_NAME) {
            type = NavType.StringType
            defaultGroup?.let {
                defaultValue = it
            }
        })
    ) { navBackStackEntry ->
        ScheduleViewer(viewModel = hiltViewModel(navBackStackEntry))
    }
}

// TODO костыль
@ExperimentalPagerApi
@Composable
fun ScheduleViewer(groupName: String) {
    val navController = rememberNavController()
    navController.enableOnBackPressed(false)
    NavHost(
        navController = navController,
        startDestination = "${Constants.ROUTE_SCHEDULE_VIEWER}/{${Constants.PARAM_GROUP_NAME}}",
    ) {
        addScheduleViewer(groupName)
    }
}