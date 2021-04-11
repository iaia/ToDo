package dev.iaiabot.todo.task

import dev.iaiabot.entity.Task

interface TaskItemViewModel : Task {
    fun onCheckedChanged(checked: Boolean)
}

internal class TaskItemViewModelImpl private constructor(
    override val completed: Boolean,
    override val id: String,
    override val title: String,
    override val order: Int,
    private val complete: () -> Unit,
) : TaskItemViewModel {

    constructor(task: Task, complete: () -> Unit) : this(
        task.completed,
        task.id,
        task.title,
        task.order,
        complete,
    )

    override fun onCheckedChanged(checked: Boolean) {
        if (checked) {
            complete.invoke()
        } else {
            // TODO: 戻す処理 restoreComplete.invoke()
        }
    }
}
