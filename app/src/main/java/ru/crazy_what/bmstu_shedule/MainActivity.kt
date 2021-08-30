package ru.crazy_what.bmstu_shedule

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.crazy_what.bmstu_shedule.data.shedule.services.ResponseResult
import ru.crazy_what.bmstu_shedule.data.shedule.services.SchedulerService
import ru.crazy_what.bmstu_shedule.ui.components.ScheduleViewer
import ru.crazy_what.bmstu_shedule.ui.screens.MainScreen
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }

        lifecycleScope.launchWhenResumed {
            val service = SchedulerService()
            val scheduler = service.schedule("ФН2-32Б")
            if (scheduler is ResponseResult.Error) {
                Log.e("MyLog", scheduler.message)
            } else if (scheduler is ResponseResult.Success) {
                setContent {
                    BMSTUScheduleTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            ScheduleViewer(scheduler = scheduler.data)
                        }
                    }
                }
            }
        }
    }
}