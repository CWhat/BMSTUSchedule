package ru.crazy_what.bmstu_shedule

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.data.shedule.services.ResponseResult
import ru.crazy_what.bmstu_shedule.data.shedule.services.SchedulerService
import ru.crazy_what.bmstu_shedule.ui.screens.MainScreen
import ru.crazy_what.bmstu_shedule.ui.screens.ScheduleViewScreen

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }

        lifecycleScope.launchWhenResumed {
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
        }
    }
}