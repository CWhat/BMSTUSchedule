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
import ru.crazy_what.bmstu_shedule.ui.base_components.SearchTopAppBar
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleList

@Composable
fun SearchTab(
    viewModel: SearchViewModel = hiltViewModel()
) {

}

@Composable
fun SelectingFaculty(groups: List<String>, viewModel: SearchViewModel) {
    val faculties = groups.map { group ->
        group.faculty
    }.distinct()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopAppBar(onValueChange = {})
        SimpleList(
            items = faculties,
            onClickItem = { _, faculty -> viewModel.showChairs(groups, faculty) })
    }
}

@Composable
fun SelectingChair(groups: List<String>, faculty: String, viewModel: SearchViewModel) {
    val chairs = groups.filter { group ->
        group.startsWith(faculty)
    }.map { group ->
        group.chair
    }.distinct()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopAppBar(onValueChange = {})
        SimpleList(
            items = chairs,
            onClickItem = { _, chair -> viewModel.showGroups(groups, chair) })
    }

}

@Composable
fun SelectingGroup(groups: List<String>, chair: String, viewModel: SearchViewModel) {
    val selectedGroups = groups.filter { group ->
        group.startsWith("$chair-")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopAppBar(onValueChange = {})
        SimpleList(items = selectedGroups, onClickItem = { _, selectedGroup ->
            viewModel.showGroupSchedule(selectedGroup)
        })
    }
}

/*fun NavGraphBuilder.addShowFaculties(navController: NavController) {
    composable()
}*/