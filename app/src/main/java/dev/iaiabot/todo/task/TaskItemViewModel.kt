package dev.iaiabot.todo.task

import dev.iaiabot.entity.Task

interface TaskItemViewModel : Task {
    fun onCheckedChanged(checked: Boolean)
}

internal class TaskItemViewModelImpl(
    private val task: Task,
    private val onCheckedChanged: (task: Task, checked: Boolean) -> Unit
) : TaskItemViewModel,
    Task by task {

    override fun onCheckedChanged(checked: Boolean) {
        onCheckedChanged(task, checked)
    }
}
