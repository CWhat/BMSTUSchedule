package ru.crazy_what.bmstu_shedule.ui.screens.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.crazy_what.bmstu_shedule.ui.components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.theme.titleStyle

sealed class MoreState {

    @Composable
    abstract fun buildUI()

    object ShowStub : MoreState() {

        @Composable
        override fun buildUI() {
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = "Меню")

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Здесь пока ничего нет", style = titleStyle)
                }
            }
        }
    }

}

class MoreStateMachine {

    private val curState = MoreState.ShowStub

    @Composable
    fun buildUI() {
        curState.buildUI()
    }
}