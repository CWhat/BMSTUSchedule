package ru.crazy_what.bmstu_shedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import ru.crazy_what.bmstu_shedule.ui.screen.ScreensConstants
import ru.crazy_what.bmstu_shedule.ui.screen.main.addMainScreen
import ru.crazy_what.bmstu_shedule.ui.screen.schedule_viewer.addScheduleScreen
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BMSTUScheduleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreensConstants.ROUTE_MAIN_SCREEN
                    ) {
                        addMainScreen(selectedGroup = { groupName ->
                            navController.navigate("${ScreensConstants.ROUTE_SCHEDULE_SCREEN}/$groupName")
                        })
                        addScheduleScreen()
                    }
                }
            }
        }
    }
}