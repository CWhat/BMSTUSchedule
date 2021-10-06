package ru.crazy_what.bmstu_shedule.ui.schedule_viewer

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage

// TODO в чем отличие StateFlow от Flow?
@Composable
fun LessonsList(lessonsListState: Flow<LessonsListState>) {

    when (val state = lessonsListState.collectAsState(initial = LessonsListState.Loading).value) {
        is LessonsListState.Loading -> CircularProgressIndicator()
        is LessonsListState.Error -> ErrorMessage(text = state.message)
        is LessonsListState.Lessons -> {
            // TODO добавить список
        }
    }

}