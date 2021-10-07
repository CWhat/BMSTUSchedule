package ru.crazy_what.bmstu_shedule.ui.base_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.crazy_what.bmstu_shedule.ui.theme.BMSTUScheduleTheme
import ru.crazy_what.bmstu_shedule.ui.theme.searchTitleStyle
import ru.crazy_what.bmstu_shedule.ui.theme.titleAppBarStyle

@Preview
@Composable
fun BasicTopAppBarPrev1() {
    BMSTUScheduleTheme {
        SimpleBasicTopAppBar(title = "Title")
    }
}

@Preview
@Composable
fun BasicTopAppBarPrev2() {
    BMSTUScheduleTheme {
        BasicTopAppBarVector(
            title = "Title",
            leftIcon = Icons.Filled.ArrowBack,
            rightIcon = Icons.Filled.Star
        )
    }
}

@Preview
@Composable
fun SearchTopAppBarPrev() {
    BMSTUScheduleTheme {
        SearchTopAppBar(onValueChange = {})
    }
}

@Composable
fun BasicTopAppBarPainter(
    title: String,
    leftIcon: Painter? = null,
    leftIconContentDescription: String = "",
    byLeftClick: () -> Unit = {},
    rightIcon: Painter? = null,
    rightIconContentDescription: String = "",
    byRightClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        if (leftIcon != null) {
            IconButton(modifier = Modifier.padding(start = 8.dp), onClick = byLeftClick) {
                SquareIcon(painter = leftIcon, contentDescription = leftIconContentDescription)
            }
        }
        Text(modifier = Modifier.padding(start = 20.dp), text = title, style = titleAppBarStyle)
        if (rightIcon != null) {
            Box(
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = byRightClick) {
                    SquareIcon(
                        painter = rightIcon,
                        contentDescription = rightIconContentDescription
                    )
                }
            }
        }
    }
}

@Composable
fun BasicTopAppBarVector(
    title: String,
    leftIcon: ImageVector? = null,
    leftIconContentDescription: String = "",
    byLeftClick: () -> Unit = {},
    rightIcon: ImageVector? = null,
    rightIconContentDescription: String = "",
    byRightClick: () -> Unit = {}
) {
    BasicTopAppBarPainter(
        title = title,
        leftIcon = if (leftIcon != null) rememberVectorPainter(image = leftIcon) else null,
        leftIconContentDescription = leftIconContentDescription,
        byLeftClick = byLeftClick,
        rightIcon = if (rightIcon != null) rememberVectorPainter(image = rightIcon) else null,
        rightIconContentDescription = rightIconContentDescription,
        byRightClick = byRightClick
    )
}

@Composable
fun SimpleBasicTopAppBar(title: String) {
    BasicTopAppBarPainter(title = title)
}


@Composable
fun SearchTopAppBar(hint: String = "Поиск", onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        // TODO как убрать подчеркивание у текста?
        // TODO как увеличить высоту текстового поля?
        TextField(
            modifier = Modifier
                .weight(1F),
            value = text,
            onValueChange = { newText -> text = newText; onValueChange(newText) },
            singleLine = true,
            shape = RoundedCornerShape(ZeroCornerSize),
            placeholder = {
                Text(text = hint, style = searchTitleStyle)
            },
            textStyle = searchTitleStyle,
            leadingIcon = {
                SquareIcon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search",
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        SquareIcon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "clear",
                            size = 32.dp,
                        )
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                // TODO убрать хардкод
                leadingIconColor = Color.Gray,
                trailingIconColor = Color.Gray,
                placeholderColor = Color.Gray
            )
        )
    }
}

