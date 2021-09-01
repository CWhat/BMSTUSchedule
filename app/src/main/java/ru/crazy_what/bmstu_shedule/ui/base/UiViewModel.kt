package ru.crazy_what.bmstu_shedule.ui.base

//import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Основано на https://dev.to/pawegio/handling-back-presses-in-jetpack-compose-50d5

interface UiViewModel {

    val uiState: StateFlow<UiAction>

    fun onAction(action: UiAction)

}

/*class Example : UiViewModel {

    private val mutableUiState = MutableStateFlow<UiAction>(UiAction.OnBack)

    override val uiState = mutableUiState

    override fun onAction(action: UiAction) {
        // Not yet implemented
    }

}*/