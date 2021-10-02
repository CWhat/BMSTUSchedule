package ru.crazy_what.bmstu_shedule.ui.base_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ru.crazy_what.bmstu_shedule.ui.HollowStar
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme

@Preview
@Composable
fun BottomNavBarPrev() {
    BMSTUScheduleTheme {
        BottomNavBar(
            icons = listOf(
                //Icons.Filled.Notifications,
                Icons.Filled.Home,
                HollowStar,
                //Icons.Filled.Star,
                Icons.Filled.Search,
                Icons.Filled.MoreVert,
                //Icons.Outlined.LocationOn
            )
        ) {}
    }
}

// TODO лучше заменить на TabRow
@Composable
fun BottomNavBar(
    icons: List<ImageVector>,
    iconsDescriptions: List<String?>? = null,
    initialPage: Int = 0,
    onClick: (num: Int) -> Unit
) {
    val state = remember {
        mutableStateOf(initialPage)
    }

    if (iconsDescriptions != null && icons.size != iconsDescriptions.size)
        error("Размер обоих списков должен быть одинаковый")

    if (state.value >= icons.size)
        error("Неверное состояние")

    BottomAppBar(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in icons.indices) {
                IconButton(onClick = {
                    if (state.value != i) {
                        state.value = i; onClick(i)
                    }
                }) {
                    SquareIcon(
                        imageVector = icons[i],
                        contentDescription = if (iconsDescriptions != null) iconsDescriptions[i] else "",
                        tint = if (state.value == i) Color.Black else Color.LightGray
                    )
                }
            }
        }
    }
}