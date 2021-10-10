package dev.iaiabot.todo.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.iaiabot.todo.component.EmailAndPassword
import dev.iaiabot.todo.component.MyTheme
import dev.iaiabot.todo.component.Progress

@Composable
fun SignUpScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailAndPassword()
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Sign In")
        }
    }
    Progress()
}

@Composable
@Preview
fun SignUpScreenPreview() {
    MyTheme {
        SignUpScreen()
    }
}
