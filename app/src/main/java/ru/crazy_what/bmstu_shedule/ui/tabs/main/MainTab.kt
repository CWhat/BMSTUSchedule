package ru.crazy_what.bmstu_shedule.ui.tabs.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.GroupScheduleViewer
import ru.crazy_what.bmstu_shedule.ui.tabs.TabsConstants

@ExperimentalPagerApi
@Composable
fun MainTab(viewModel: MainTabViewModel) {
    when (val state = viewModel.state.value) {
        is MainState.Loading -> {
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = "Загрузка...")
                LoadView()
            }
        }
        is MainState.MainGroup -> {
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = state.groupName)
                GroupScheduleViewer(groupScheduler = state.groupScheduler)
            }
        }
        is MainState.Error -> {
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = "Ошибка")
                ErrorMessage(text = "Произошла ошибка: ${state.message}")
            }
        }
    }
}

@ExperimentalPagerApi
fun NavGraphBuilder.addMainTab() {
    composable(
        route = TabsConstants.ROUTE_MAIN_TAB,
    ) { navBackStackEntry ->
        MainTab(viewModel = hiltViewModel(navBackStackEntry))
    }
}