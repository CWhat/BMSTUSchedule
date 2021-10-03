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
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components.ScheduleViewer

@ExperimentalPagerApi
@Composable
fun ScheduleViewerScreen(
    viewModel: ScheduleViewerViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val groupName = viewModel.groupName.value

    Column(modifier = Modifier.fillMaxSize()) {
        SimpleBasicTopAppBar(title = groupName)

        when (state) {
            is ScheduleViewerState.Loading -> LoadView()
            is ScheduleViewerState.Error -> ErrorMessage(
                text = "При загрузке расписания произошла ошибка: ${state.message}",
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
            )
            is ScheduleViewerState.Schedule -> ScheduleViewer(scheduler = state.scheduler)
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