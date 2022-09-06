package ru.crazy_what.bmstu_shedule.ui.tabs.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.crazy_what.bmstu_shedule.ui.base_components.SearchTopAppBar
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.tabs.TabsConstants
import ru.crazy_what.bmstu_shedule.ui.tabs.search.components.SearchGroupList

@Composable
fun SearchTab(
    selectedGroup: (route: String) -> Unit,
    viewModel: SearchViewModel,
) {
    when (val state = viewModel.state.value) {
        is SearchState.Loading -> {
        }
        is SearchState.Error -> {
            // TODO сделать красивое сообщение об ошибке
            Column(modifier = Modifier.fillMaxSize()) {
                SimpleBasicTopAppBar(title = "Ошибка")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Что-то пошло не так: ${state.message}")
                }
            }
        }
        is SearchState.ShowGroups -> {
            var request by remember { mutableStateOf("") }
            Column(modifier = Modifier.fillMaxSize()) {
                SearchTopAppBar(onValueChange = { newRequest -> request = newRequest })
                SearchGroupList(groups = state.groups, request = request, onClick = selectedGroup)
            }
        }
    }
}

fun NavGraphBuilder.addSearchTab(
    selectedGroup: (groupName: String) -> Unit,
) {
    composable(route = TabsConstants.ROUTE_SEARCH_TAB) { navBackStackEntry ->
        Column(modifier = Modifier.fillMaxSize()) {
            SearchTab(
                selectedGroup = selectedGroup,
                viewModel = hiltViewModel(navBackStackEntry)
            )
        }
    }
}