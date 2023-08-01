package ru.crazy_what.bmstu_shedule.ui.base_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// TODO сделать
// TODO добавить отложенный показ (чтобы не было моментов, когда "кружочек" показывается на мгновение)
@Preview(showSystemUi = true)
@Composable
fun LoadView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}