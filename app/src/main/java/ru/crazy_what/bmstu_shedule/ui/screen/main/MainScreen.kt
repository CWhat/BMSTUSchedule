package ru.crazy_what.bmstu_shedule.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.ui.screen.ScreensConstants
import ru.crazy_what.bmstu_shedule.ui.tabs.TabsConstants
import ru.crazy_what.bmstu_shedule.ui.tabs.components.MainScreenBottomNavBar
import ru.crazy_what.bmstu_shedule.ui.tabs.createMainScreenNavGraph

@ExperimentalPagerApi
@Composable
fun MainScreen(
    selectedGroup: (String) -> Unit,
) {
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(
            startDestination = TabsConstants.ROUTE_MAIN_TAB,
            navController = navController,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
        ) {
            createMainScreenNavGraph(
                selectedGroup = selectedGroup,
            )
        }
        MainScreenBottomNavBar(navController = navController)
    }
}

@ExperimentalPagerApi
fun NavGraphBuilder.addMainScreen(
    selectedGroup: (String) -> Unit,
) {
    composable(route = ScreensConstants.ROUTE_MAIN_SCREEN) {
        MainScreen(selectedGroup)
    }
}