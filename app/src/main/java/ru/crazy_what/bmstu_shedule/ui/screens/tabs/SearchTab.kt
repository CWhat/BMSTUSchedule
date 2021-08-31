package ru.crazy_what.bmstu_shedule.ui.screens.tabs

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.MainViewModel
import ru.crazy_what.bmstu_shedule.data.shedule.services.ResponseResult
import ru.crazy_what.bmstu_shedule.ui.components.GroupSelectionList
import ru.crazy_what.bmstu_shedule.ui.components.SearchTopAppBar
import ru.crazy_what.bmstu_shedule.ui.components.SimpleBasicTopAppBar

interface SearchViewModel {

    suspend fun loadGroups()
    suspend fun loadScheduler(group: String)

}

sealed class SearchState(protected val viewModel: SearchViewModel) {
    @Composable
    abstract fun buildUI()

    class Loading(viewModel: SearchViewModel) : SearchState(viewModel) {

        @Composable
        override fun buildUI() {
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = "Загрузка")
                LaunchedEffect("initSearchTab") {
                    viewModel.loadGroups()
                }
            }
        }

    }

    class ShowList(private val groups: List<String>, viewModel: SearchViewModel) :
        SearchState(viewModel) {

        @Composable
        override fun buildUI() {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchTopAppBar(onValueChange = {})
                val context = LocalContext.current
                GroupSelectionList(groups = groups, select = {
                    Toast.makeText(context, "Оно работает!!!", Toast.LENGTH_SHORT).show()
                })
            }
        }

    }
}

class SearchStateMachine(private val viewModel: MainViewModel) : SearchViewModel {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    init {
        coroutineScope.launch {
            viewModel.groupsFlow.collect { result ->
                when (result) {
                    is ResponseResult.Error -> {
                    }
                    is ResponseResult.Success -> curState =
                        SearchState.ShowList(result.data, this@SearchStateMachine)
                }
            }
        }
    }

    private var curState: SearchState by mutableStateOf(SearchState.Loading(this))

    @Composable
    fun buildUI() {
        curState.buildUI()
    }

    override suspend fun loadGroups() {
        viewModel.loadGroups()
    }

    override suspend fun loadScheduler(group: String) {
        viewModel.loadSchedule(group)
    }
}