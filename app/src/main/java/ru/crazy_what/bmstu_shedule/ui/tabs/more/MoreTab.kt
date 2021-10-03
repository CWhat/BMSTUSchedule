package ru.crazy_what.bmstu_shedule.ui.tabs.more

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.theme.infoStyle

@Composable
fun MoreTab() {
    val viewModel = viewModel<MoreViewModel>()
    val numberOfVisits by viewModel.numberOfVisits

    Column(modifier = Modifier.fillMaxSize()) {
        SimpleBasicTopAppBar(title = "Меню")

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Column {
                Text(text = "Посещено занятий по физ-ре: $numberOfVisits", style = infoStyle)
                Button(onClick = { viewModel.addVisit() }) {
                    Text(text = "Добавить")
                }
            }
        }
    }
}