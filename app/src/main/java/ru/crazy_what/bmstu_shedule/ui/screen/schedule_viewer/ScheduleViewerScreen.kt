package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.ui.HollowStar
import ru.crazy_what.bmstu_shedule.ui.base_components.BasicTopAppBarVector
import ru.crazy_what.bmstu_shedule.ui.screen.ScreensConstants
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.ScheduleViewer

@ExperimentalPagerApi
@Composable
fun ScheduleViewerScreen(
    viewModel: ScheduleScreenViewModel,
    onBack: () -> Unit,
) {
    val groupName = viewModel.groupName.value
    // TODO добавить добавление в закладки
    val isBookmark = viewModel.isBookmarks.value

    Column(modifier = Modifier.fillMaxSize()) {
        BasicTopAppBarVector(
            title = groupName,
            leftIcon = Icons.Filled.ArrowBack,
            byLeftClick = onBack,
            rightIcon = if (isBookmark) Icons.Filled.Star else HollowStar,
            byRightClick = { viewModel.addBookmark() },
        )
        ScheduleViewer(scheduleViewerState = viewModel.state.value)
    }
}

@ExperimentalPagerApi
fun NavGraphBuilder.addScheduleScreen(onBack: () -> Unit) {
    composable(
        route = "${ScreensConstants.ROUTE_SCHEDULE_SCREEN}/{${Constants.PARAM_GROUP_UUID}}",
        arguments = listOf(navArgument(name = Constants.PARAM_GROUP_UUID) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        ScheduleViewerScreen(viewModel = hiltViewModel(navBackStackEntry), onBack = onBack)
    }
}