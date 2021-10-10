package dev.iaiabot.todo.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.iaiabot.todo.component.EmailAndPassword
import dev.iaiabot.todo.component.MyTheme
import dev.iaiabot.todo.component.Progress

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel
) {
    val email: String by viewModel.email.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val nowLoading: Boolean by viewModel.nowLoading.observeAsState(false)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailAndPassword(
            email, { text -> viewModel.onChangeEmail(text) },
            password, { text -> viewModel.onChangePassword(text) },
        )
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = {
                viewModel.onClickSignUp()
            }
        ) {
            Text(text = "Sign In")
        }
    }
    Progress(nowLoading)
}

@Composable
@Preview
fun SignUpScreenPreview() {
    MyTheme {
        SignUpScreen(
            viewModel = viewModel()
        )
    }
}
