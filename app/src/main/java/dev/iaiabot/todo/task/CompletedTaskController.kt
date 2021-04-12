package dev.iaiabot.todo.task

import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.todo.listItemTask

internal class CompletedTaskController(
) : TypedEpoxyController<List<TaskItemViewModel>>() {
    override fun buildModels(data: List<TaskItemViewModel>) {
        data.forEach {
            listItemTask {
                id(it.id)
                title(it.title)
                completed(it.completed)
                onCheckedChanged { _, isChecked ->
                    it.onCheckedChanged(isChecked)
                }
            }
        }
    }
}
