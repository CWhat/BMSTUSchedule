package ru.crazy_what.bmstu_shedule.ui.schedule_viewer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components.DateCircleLinePrev
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.components.LessonsListPrev

@Composable
fun ScheduleViewerScreen(
    viewModel: ScheduleViewerViewModel = viewModel()
) {
    val groupName = viewModel.groupName.value
    Column(modifier = Modifier.fillMaxSize()) {
        SimpleBasicTopAppBar(title = groupName)

        // TODO сделать
        DateCircleLinePrev()
        LessonsListPrev()
    }
}