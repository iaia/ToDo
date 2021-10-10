package dev.iaiabot.todo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EmailAndPassword(
    email: String,
    emailOnChange: (String) -> Unit,
    password: String,
    passwordOnChange: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Email(email, emailOnChange)
        Password(password, passwordOnChange)
    }
}

@Composable
fun Email(text: String, onChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onChange,
        label = { Text("email") }
    )
}

@Composable
fun Password(text: String, onChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onChange,
        label = { Text("password") }
    )
}

@Composable
@Preview
fun EmailAndPasswordPreview() {
    EmailAndPassword(
        "email",
        { _ -> },
        "password",
        { _ -> }
    )
}
