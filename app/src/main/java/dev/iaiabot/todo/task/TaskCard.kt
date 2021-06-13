package dev.iaiabot.todo.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
                    .padding(15.dp)
                    .clickable {
                        viewModel.toggleComplete(task)
                    },
            ) {
                Text(task.title)
            }
        }
    }
}
