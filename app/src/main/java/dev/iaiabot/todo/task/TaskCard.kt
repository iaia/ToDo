package dev.iaiabot.todo.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.iaiabot.entity.Task

@Composable
fun TaskCard(
    viewModel: TaskViewModel,
    tasks: List<Task>
) {
    Column(
        Modifier.verticalScroll(
            rememberScrollState()
        )
    ) {
        tasks.forEach { task ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        viewModel.toggleComplete(task)
                    },
            ) {
                val title = remember { mutableStateOf(task.title) }
                TextField(
                    value = title.value,
                    onValueChange = {
                        title.value = it
                        viewModel.onChangeTask(task, it)
                    },
                )
            }
        }
    }
}
