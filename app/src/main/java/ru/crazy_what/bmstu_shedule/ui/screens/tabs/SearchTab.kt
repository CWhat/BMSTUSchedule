package ru.crazy_what.bmstu_shedule.ui.screens.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.crazy_what.bmstu_shedule.*
import ru.crazy_what.bmstu_shedule.data.shedule.Scheduler
import ru.crazy_what.bmstu_shedule.data.shedule.services.ResponseResult
import ru.crazy_what.bmstu_shedule.ui.components.*
import ru.crazy_what.bmstu_shedule.ui.theme.titleStyle

interface SearchViewModel {

    suspend fun loadGroups()
    suspend fun loadScheduler(group: String)

    fun navigateUp()

    fun showChairs(groups: List<String>, faculty: String)
    fun showGroups(groups: List<String>, chair: String)

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

    class Error(private val message: String, viewModel: SearchViewModel) :
        SearchState(viewModel) {

        @Composable
        override fun buildUI() {
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = "Ошибка")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = message, style = titleStyle)
                }
            }
        }

    }

    class ShowSchedule(
        private val scheduler: Scheduler,
        private val group: String,
        viewModel: SearchViewModel
    ) :
        SearchState(viewModel) {

        @ExperimentalPagerApi
        @Composable
        override fun buildUI() {
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = group)
                ScheduleViewer(scheduler)
            }
        }

    }

    class ShowFaculties(
        private val groups: List<String>,
        viewModel: SearchViewModel,
    ) : SearchState(viewModel) {

        private val faculties = groups.map { group ->
            // TODO получается, что есть кафедра ЮР, так как есть группы ЮР-91, ЮР-111 и ЮР-113, где эта функция отрабатывает неправильно
            val index = group.indexOfFirst { it.isDigit() }
            group.substring(0, index)
        }.distinct()

        @Composable
        override fun buildUI() {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchTopAppBar(onValueChange = {})
                SimpleList(
                    items = faculties,
                    onClick = { _, faculty -> viewModel.showChairs(groups, faculty) })
            }
        }

    }

    class ShowChairs(
        private val groups: List<String>,
        private val faculty: String,
        viewModel: SearchViewModel,
    ) : SearchState(viewModel) {

        private val chairs = groups.filter { group ->
            group.startsWith(faculty)
        }.map { group ->
            val index = group.indexOfFirst { it == '-' }
            group.substring(0, index)
        }.distinct()

        @Composable
        override fun buildUI() {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchTopAppBar(onValueChange = {})
                SimpleList(
                    items = chairs,
                    onClick = { _, chair -> viewModel.showGroups(groups, chair) })
            }
        }

    }

    class ShowGroups(
        groups: List<String>,
        private val chair: String,
        viewModel: SearchViewModel,
    ) : SearchState(viewModel) {

        private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

        private val selectedGroups = groups.filter { group ->
            group.startsWith(chair)
        }

        @Composable
        override fun buildUI() {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchTopAppBar(onValueChange = {})
                SimpleList(items = selectedGroups, onClick = { _, selectedGroup ->
                    coroutineScope.launch {
                        viewModel.loadScheduler(selectedGroup)
                    }
                })
            }
        }

    }


}

class SearchStateMachine(private val viewModel: MainViewModel) : SearchViewModel {

    private val stackState: Stack<SearchState> = mutableStateListOf(SearchState.Loading(this))

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    init {
        coroutineScope.launch {
            viewModel.groupsFlow.collect { result ->
                //curState = when (result) {
                stackState.push(
                    when (result) {
                        is ResponseResult.Error -> SearchState.Error(
                            result.message,
                            this@SearchStateMachine
                        )
                        is ResponseResult.Success -> /*SearchState.ShowList(
                            result.data,
                            this@SearchStateMachine
                        )*/
                            SearchState.ShowFaculties(result.data, this@SearchStateMachine)
                    }
                )
            }
        }

        coroutineScope.launch {
            viewModel.scheduleFlow.collect { result ->
                //curState = when (result) {
                stackState.push(
                    when (result) {
                        is ResponseResult.Error -> SearchState.Error(
                            result.message,
                            this@SearchStateMachine
                        )
                        is ResponseResult.Success -> SearchState.ShowSchedule(
                            result.data,
                            curGroup,
                            this@SearchStateMachine
                        )
                    }
                )
            }
        }
    }

    //private var curState: SearchState by mutableStateOf(SearchState.Loading(this))

    @Composable
    fun buildUI() {
        //curState.buildUI()
        stackState.peek()?.buildUI()
    }

    override suspend fun loadGroups() {
        viewModel.loadGroups()
    }

    private var curGroup = "NONE"
    override suspend fun loadScheduler(group: String) {
        curGroup = group
        viewModel.loadSchedule(group)
    }

    override fun navigateUp() {
        stackState.pop()
    }

    override fun showChairs(groups: List<String>, faculty: String) {
        stackState.push(SearchState.ShowChairs(groups, faculty, this))
    }

    override fun showGroups(groups: List<String>, chair: String) {
        stackState.push(SearchState.ShowGroups(groups, chair, this))
    }
}