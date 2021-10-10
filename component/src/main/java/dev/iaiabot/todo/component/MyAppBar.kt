package dev.iaiabot.todo.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyAppBar() {
    TopAppBar(
        title = { Text("TODO") },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.Logout, contentDescription = "logout")
            }
        }
    )
}

@Composable
@Preview
fun MyAppBarPreview() {
    MyTheme {
        MyAppBar()
    }
}
