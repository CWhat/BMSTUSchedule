package ru.crazy_what.bmstu_shedule.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler
import ru.crazy_what.bmstu_shedule.ui.components.BottomNavBarPrev
import ru.crazy_what.bmstu_shedule.ui.components.DateCircleLine
import ru.crazy_what.bmstu_shedule.ui.components.ScheduleViewer
import ru.crazy_what.bmstu_shedule.ui.components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@ExperimentalPagerApi
@Composable
fun ScheduleViewScreen(title: String, scheduler: Scheduler) {
    BMSTUScheduleTheme(darkTheme = false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = title)

                Column(modifier = Modifier.weight(1F)) {
                    ScheduleViewer(scheduler = scheduler)
                }

                BottomNavBarPrev()
            }
        }
    }
}