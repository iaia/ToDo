package dev.iaiabot.todo.component

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable

// MainActivityに持ってく
@Composable
fun MainContent(
    bodyContent: @Composable () -> Unit
) {
    MyTheme {
        Scaffold(
            topBar = { MyAppBar() },
            content = { bodyContent.invoke() }
        )
    }
}
