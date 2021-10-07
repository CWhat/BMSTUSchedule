package ru.crazy_what.bmstu_shedule.ui.tabs.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.ScheduleViewer

@ExperimentalPagerApi
@Composable
fun MainTab(viewModel: MainViewModel = hiltViewModel()) {
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
                ScheduleViewer(groupName = state.groupName)
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