package ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.data.fakeData
import ru.crazy_what.bmstu_shedule.domain.model.GroupSchedule
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@ExperimentalPagerApi
@Composable
fun ScheduleViewer(scheduleViewerState: ScheduleViewerState) {
    Column(modifier = Modifier.fillMaxSize()) {
        when (scheduleViewerState) {
            is ScheduleViewerState.Loading -> LoadView()
            is ScheduleViewerState.Schedule ->
                GroupScheduleViewer(
                    modifier = Modifier.fillMaxSize(),
                    groupSchedule = scheduleViewerState.scheduler,
                )
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

@Composable
fun GroupScheduleViewer(
    modifier: Modifier = Modifier,
    groupSchedule: GroupSchedule,
) {
    CalendarTabs(
        modifier = modifier,
        start = groupSchedule.start,
        end = groupSchedule.end,
        current = groupSchedule.currentTime,
    ) {
        val schedule =
            produceState<GroupLessonsListState>(initialValue = GroupLessonsListState.Loading) {
                val schedule = groupSchedule.getSchedule(it)
                value = GroupLessonsListState.Lessons(schedule)
            }

        LessonsList(state = schedule.value)
    }
}

@Preview(showSystemUi = true)
@Composable
fun GroupScheduleViewerPrev() {
    val groupSchedule = remember { fakeData("ФН2-61Б") }

    BMSTUScheduleTheme(darkTheme = false) {
        GroupScheduleViewer(
            modifier = Modifier.fillMaxSize(),
            groupSchedule = groupSchedule,
        )
    }
}