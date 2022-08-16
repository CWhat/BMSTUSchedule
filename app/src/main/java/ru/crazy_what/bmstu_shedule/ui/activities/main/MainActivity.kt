package ru.crazy_what.bmstu_shedule.ui.activities.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import ru.crazy_what.bmstu_shedule.date.Month
import ru.crazy_what.bmstu_shedule.rememberAppState
import ru.crazy_what.bmstu_shedule.ui.screen.ScreensConstants
import ru.crazy_what.bmstu_shedule.ui.screen.load_schedule.addLoadScheduleScreen
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.addScheduleScreen
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.CalendarTabs
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.GroupScheduleViewerPrev
import ru.crazy_what.bmstu_shedule.ui.tabs.TabsConstants
import ru.crazy_what.bmstu_shedule.ui.tabs.components.MainBottomBar
import ru.crazy_what.bmstu_shedule.ui.tabs.createMainScreenNavGraph
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO здесь мы должны сначала проверять загружено ли расписание и выбрана ли основная группа

        setContent {
            BMSTUScheduleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val appState = rememberAppState()
                    Column(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F),
                            navController = appState.navController,
                            // TODO выбирать в зависимости от того, скачано ли расписание и выбрана ли основная группа
                            //startDestination = ScreensConstants.ROUTE_MAIN_SCREEN,
                            startDestination = ScreensConstants.ROUTE_LOAD_SCHEDULE,
                        ) {
                            navigation(
                                startDestination = TabsConstants.ROUTE_MAIN_TAB,
                                route = ScreensConstants.ROUTE_MAIN_SCREEN,
                            ) {
                                createMainScreenNavGraph(
                                    selectedGroup = { groupName ->
                                        appState.navigateToScheduleViewer(groupName)
                                    }
                                )
                            }
                            addScheduleScreen()
                            addLoadScheduleScreen {
                                appState.navigateToMainScreen()
                            }
                        }
                        if (appState.shouldShowBottomBar) {
                            MainBottomBar(
                                tabs = appState.bottomBarTabs,
                                currentRoute = appState.currentRoute!!,
                                navigateToRoute = appState::navigateToBottomBarRoute
                            )
                        }
                    }
                }
            }
        }
    }
}