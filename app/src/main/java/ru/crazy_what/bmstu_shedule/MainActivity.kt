package ru.crazy_what.bmstu_shedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.ui.screens.CompositionViewModel
import ru.crazy_what.bmstu_shedule.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(CompositionViewModel provides viewModel()) {
                MainScreen()
            }
        }

        /*lifecycleScope.launchWhenResumed {
            val group = "ФН2-32Б"
            val service = SchedulerService()
            val scheduler = service.schedule(group)
            if (scheduler is ResponseResult.Error) {
                Log.e("MyLog", scheduler.message)
            } else if (scheduler is ResponseResult.Success) {
                setContent {
                    ScheduleViewScreen(title = group, scheduler = scheduler.data)
                }
            }
        }*/
    }
}