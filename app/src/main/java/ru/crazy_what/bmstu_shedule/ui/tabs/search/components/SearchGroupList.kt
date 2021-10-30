package ru.crazy_what.bmstu_shedule.ui.tabs.search.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.crazy_what.bmstu_shedule.common.normalizeGroupName
import ru.crazy_what.bmstu_shedule.ui.base_components.SimpleList

@Composable
fun SearchGroupList(
    groups: List<String>,
    request: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val normalizeRequest = normalizeGroupName(request)

    val foundGroups = groups.filter { group -> group.startsWith(normalizeRequest) }

    // TODO добавить placeholder
    SimpleList(
        modifier = modifier,
        items = foundGroups,
        onClickItem = { _, group -> onClick(group) }
    )
}