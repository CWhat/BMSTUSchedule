package ru.crazy_what.bmstu_shedule.ui.activities.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import ru.crazy_what.bmstu_shedule.date.Month
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.CalendarTabs
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.components.GroupScheduleViewerPrev
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO здесь мы должны сначала проверять загружено ли расписание и выбрана ли основная группа

        setContent {
            /*BMSTUScheduleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    /*val appState = rememberAppState()
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
                    }*/

                    val start = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.SEPTEMBER)
                            set(Calendar.DAY_OF_MONTH, 1)
                        }
                    }

                    val end = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.OCTOBER)
                            set(Calendar.DAY_OF_MONTH, 5)
                        }
                    }

                    val current = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.SEPTEMBER)
                            set(Calendar.DAY_OF_MONTH, 25)
                        }
                    }

                    /*val start = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.SEPTEMBER)
                            set(Calendar.DAY_OF_MONTH, 1)
                        }
                    }

                    val end = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.SEPTEMBER)
                            set(Calendar.DAY_OF_MONTH, 6)
                        }
                    }

                    val current = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.SEPTEMBER)
                            set(Calendar.DAY_OF_MONTH, 3)
                        }
                    }*/

                    /*val start = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.SEPTEMBER)
                            set(Calendar.DAY_OF_MONTH, 1)
                        }
                    }

                    val end = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.SEPTEMBER)
                            set(Calendar.DAY_OF_MONTH, 3)
                        }
                    }

                    val current = remember {
                        Calendar.getInstance().apply {
                            set(Calendar.YEAR, 2022)
                            set(Calendar.MONTH, Calendar.SEPTEMBER)
                            set(Calendar.DAY_OF_MONTH, 2)
                        }
                    }*/

                    CalendarTabs(
                        modifier = Modifier.fillMaxSize(),
                        start = start,
                        end = end,
                        current = current,
                    ) {
                        val year = remember { it.get(Calendar.YEAR) }
                        val month = remember { Month.from(it) }
                        val day = remember { it.get(Calendar.DAY_OF_MONTH) }

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Blue),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "$day ${month.toLangString()} $year",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }


                //val coroutineScope = rememberCoroutineScope()
                //val loader = remember {
                //    ScheduleLoader(coroutineScope) {
                //        delay(1000)
                //        it
                //    }
                //}


                /*val state = rememberPagerState()
                HorizontalPager(count = 100, state = state) { page ->
                    //val data = loader.get(page)

                    val data: State<Int?> = produceState<Int?>(initialValue = null, page) {
                        delay(2000)
                        value = page
                    }

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Blue),
                        contentAlignment = Alignment.Center) {
                        Text(
                            if (data.value != null) "${data.value}" else "data not loaded",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                        )
                    }
                }*/
            }*/

            GroupScheduleViewerPrev()
        }
    }
}