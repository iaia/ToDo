package dev.iaiabot.todo.task

import dev.iaiabot.entity.Task

interface TaskItemViewModel : Task {
    fun onCheckedChanged(checked: Boolean)
}
