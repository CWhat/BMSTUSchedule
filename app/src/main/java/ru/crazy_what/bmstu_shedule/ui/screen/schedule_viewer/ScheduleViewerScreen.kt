package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.screen.ScreensConstants
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.ScheduleViewer

@ExperimentalPagerApi
@Composable
fun ScheduleViewerScreen(
    viewModel: ScheduleScreenViewModel,
) {
    val groupName = viewModel.groupName.value
    // TODO добавить добавление в закладки
    val isBookmark = viewModel.isBookmarks.value

    Column(modifier = Modifier.fillMaxSize()) {
        SimpleBasicTopAppBar(title = groupName)
        ScheduleViewer(groupName)
    }
}

@ExperimentalPagerApi
fun NavGraphBuilder.addScheduleScreen() {
    composable(
        route = "${ScreensConstants.ROUTE_SCHEDULE_SCREEN}/{${Constants.PARAM_GROUP_NAME}}",
        arguments = listOf(navArgument(name = Constants.PARAM_GROUP_NAME) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        ScheduleViewerScreen(viewModel = hiltViewModel(navBackStackEntry))
    }
}