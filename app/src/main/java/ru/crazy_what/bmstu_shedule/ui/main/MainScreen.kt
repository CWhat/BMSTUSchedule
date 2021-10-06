package ru.crazy_what.bmstu_shedule.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.MainViewModel
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.ui.HollowStar
import ru.crazy_what.bmstu_shedule.ui.base_components.BottomNavBar
import ru.crazy_what.bmstu_shedule.ui.schedule_viewer.addScheduleViewer
import ru.crazy_what.bmstu_shedule.ui.tabs.SearchStateMachine
import ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks.BookmarksTab
import ru.crazy_what.bmstu_shedule.ui.tabs.more.MoreTab
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

val LocalViewModel = compositionLocalOf<MainViewModel> { error("ViewModel не проброшен") }

@ExperimentalPagerApi
@Composable
fun MainScreen() {
    val pagerState = rememberPagerState(pageCount = 4, initialOffscreenLimit = 3)
    val coroutineScope = rememberCoroutineScope()

    val searchStateMachine = SearchStateMachine(LocalViewModel.current)

    val mainNavController = rememberNavController()
    val mainNavGraph = remember {
        mainNavController.createGraph(
            startDestination = "${Constants.ROUTE_SCHEDULE_VIEWER}/${Constants.PARAM_GROUP_NAME}",
        ) {
            addScheduleViewer("ФН2-32Б")
        }
    }
    val bookmarksNavController = rememberNavController()
    val searchNavController = rememberNavController()
    val moreNavController = rememberNavController()

    BMSTUScheduleTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    state = pagerState,
                    dragEnabled = false,
                    verticalAlignment = Alignment.Top
                ) { page ->
                    when (page) {
                        0 -> {
                            NavHost(
                                navController = mainNavController,
                                graph = mainNavGraph,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        1 -> {
                            // TODO добавить открытие расписания
                            BookmarksTab(clickOnGroups = {})
                        }
                        2 -> {
                            searchStateMachine.buildUI()
                        }
                        3 -> {
                            MoreTab()
                        }
                    }
                }

                BottomNavBar(
                    icons = listOf(
                        //Icons.Filled.Notifications,
                        Icons.Filled.Home,
                        HollowStar,
                        //Icons.Filled.Star,
                        Icons.Filled.Search,
                        Icons.Filled.MoreVert,
                    ),
                    initialPage = 0
                ) { page ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page)
                    }
                }

                /*BottomNavigation() {

                }*/

            }
        }
    }
}