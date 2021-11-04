package ru.crazy_what.bmstu_shedule.ui.screen.load_schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.InfoMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.screen.ScreensConstants
import ru.crazy_what.bmstu_shedule.ui.screen.load_schedule.components.LoadProgress
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@Composable
fun LoadScheduleScreen(viewModel: LoadScheduleViewModel, onReady: () -> Unit) {
    val state = viewModel.state.value
    LoadScheduleScreen(state, onReady)
}

@Composable
fun LoadScheduleScreen(loadScheduleState: LoadScheduleState, onReady: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        SimpleBasicTopAppBar(title = "Загрузка")
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center,
        ) {
            when (loadScheduleState) {
                is LoadScheduleState.Init -> {
                    InfoMessage(text = "Мы начинаем загрузка. Надеемся, что она пройдет для вас быстро!")
                }
                is LoadScheduleState.InProgress -> {
                    LoadProgress(
                        current = loadScheduleState.current,
                        total = loadScheduleState.total
                    )
                }
                is LoadScheduleState.Error -> {
                    ErrorMessage(text = "Произошла ошибка: ${loadScheduleState.message}. Попробуйте позже")
                }
                is LoadScheduleState.Ready -> LaunchedEffect("launch") { onReady() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadScheduleScreenInitPrev() {
    BMSTUScheduleTheme {
        LoadScheduleScreen(loadScheduleState = LoadScheduleState.Init) {}
    }
}

@Preview(showBackground = true)
@Composable
fun LoadScheduleScreenInProgressPrev() {
    BMSTUScheduleTheme {
        LoadScheduleScreen(loadScheduleState = LoadScheduleState.InProgress(123, 200)) {}
    }
}

@Preview(showBackground = true)
@Composable
fun LoadScheduleScreenErrorPrev() {
    BMSTUScheduleTheme {
        LoadScheduleScreen(loadScheduleState = LoadScheduleState.Error("У вас отключен интернет")) {}
    }
}

fun NavGraphBuilder.addLoadScheduleScreen(onReady: () -> Unit) {
    composable(route = ScreensConstants.ROUTE_LOAD_SCHEDULE) { navBackStackEntry ->
        LoadScheduleScreen(viewModel = hiltViewModel(navBackStackEntry), onReady = onReady)
    }
}