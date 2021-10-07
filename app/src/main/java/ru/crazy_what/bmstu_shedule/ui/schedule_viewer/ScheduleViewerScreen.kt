package ru.crazy_what.bmstu_shedule.ui.schedule_viewer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.schedule.StudyDayInfo
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components.NewScheduleViewer

@ExperimentalPagerApi
@Composable
fun ScheduleViewerScreen(
    viewModel: ScheduleViewerViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val groupName = viewModel.groupName.value
    //val isBookmark = viewModel.isBookmarks.value

    Column(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is ScheduleViewerState.Loading -> LoadView()
            // TODO поменять
            is ScheduleViewerState.Schedule ->
                with(state.scheduler) {
                    NewScheduleViewer(
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
        route = "${Constants.ROUTE_SCHEDULE_VIEWER}/${Constants.PARAM_GROUP_NAME}",
        arguments = listOf(navArgument(name = Constants.PARAM_GROUP_NAME) {
            type = NavType.StringType
            defaultGroup?.let {
                defaultValue = it
            }
        })
    ) {
        ScheduleViewerScreen()
    }
}

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(groupName: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "${Constants.ROUTE_SCHEDULE_VIEWER}/${Constants.PARAM_GROUP_NAME}",
    ) {
        addScheduleViewer(groupName)
    }
}