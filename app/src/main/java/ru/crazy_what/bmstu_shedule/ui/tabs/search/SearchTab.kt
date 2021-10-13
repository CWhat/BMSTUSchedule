package ru.crazy_what.bmstu_shedule.ui.tabs.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.crazy_what.bmstu_shedule.common.chair
import ru.crazy_what.bmstu_shedule.common.faculty
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.SearchTopAppBar
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleList
import ru.crazy_what.bmstu_shedule.ui.tabs.TabsConstants

@Composable
fun SearchTab(
    selectedGroup: (route: String) -> Unit,
    viewModel: SearchViewModel,
) {
    when (val state = viewModel.state.value) {
        is SearchState.Loading -> { /*TODO*/
        }
        is SearchState.Error -> {
            ErrorMessage(text = state.message)
        }
        is SearchState.ShowFaculties -> {
            SelectingFaculty(groups = state.groups, clickOnFaculty = { groups, faculty ->
                viewModel.showChairs(groups, faculty)
            })
        }
        is SearchState.ShowChairs -> {
            SelectingChair(
                groups = state.groups,
                faculty = state.faculty,
                clickOnChair = { groups, chair ->
                    viewModel.showGroups(groups, chair)
                })
        }
        is SearchState.ShowGroups -> {
            SelectingGroup(
                groups = state.groups,
                chair = state.chair,
                clickOnGroup = selectedGroup,
            )
        }
    }
}

@Composable
fun SelectingFaculty(
    groups: List<String>,
    clickOnFaculty: (groups: List<String>, faculty: String) -> Unit,
) {
    val faculties = groups.map { group ->
        group.faculty
    }.distinct()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopAppBar(onValueChange = {})
        SimpleList(
            items = faculties,
            onClickItem = { _, faculty -> clickOnFaculty(groups, faculty) })
    }
}

@Composable
fun SelectingChair(
    groups: List<String>,
    faculty: String,
    clickOnChair: (groups: List<String>, chair: String) -> Unit,
) {
    val chairs = groups.filter { group ->
        group.startsWith(faculty)
    }.map { group ->
        group.chair
    }.distinct()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopAppBar(onValueChange = {})
        SimpleList(
            items = chairs,
            onClickItem = { _, chair -> clickOnChair(groups, chair) })
    }

}

@Composable
fun SelectingGroup(
    groups: List<String>,
    chair: String,
    clickOnGroup: (group: String) -> Unit,
) {
    val selectedGroups = groups.filter { group ->
        group.startsWith("$chair-")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopAppBar(onValueChange = {})
        SimpleList(items = selectedGroups, onClickItem = { _, selectedGroup ->
            clickOnGroup(selectedGroup)
        })
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