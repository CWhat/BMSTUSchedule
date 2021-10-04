package ru.crazy_what.bmstu_shedule.ui.tabs.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.crazy_what.bmstu_shedule.ui.base_components.ErrorMessage
import ru.crazy_what.bmstu_shedule.ui.base_components.LoadView
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleBasicTopAppBar
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleList
import ru.crazy_what.bmstu_shedule.ui.theme.littleTitleStyle

@Composable
fun BookmarksTab(
    clickOnGroups: (String) -> Unit,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()) {
        SimpleBasicTopAppBar(title = "Закладки")

        when (state) {
            is BookmarksState.Loading -> {
                LoadView()
            }
            is BookmarksState.Bookmarks -> {
                SimpleList(
                    items = state.groups,
                    onClickItem = { _, group -> clickOnGroups(group) },
                    placeholder = {
                        Text(
                            text = "Здесь пока пусто",
                            style = littleTitleStyle,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                )
            }
            is BookmarksState.Error -> {
                ErrorMessage(
                    text = "При загрузке закладок произошла ошибка: ${state.message}",
                    modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}