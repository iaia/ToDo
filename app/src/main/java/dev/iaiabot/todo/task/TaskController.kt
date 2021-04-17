package dev.iaiabot.todo.task

import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.todo.listItemTask
import dev.iaiabot.todo.listItemTaskCompleted
import dev.iaiabot.todo.listItemTaskIncomplete

internal class TaskController(
) : TypedEpoxyController<List<TaskItemViewModel>>() {
    override fun buildModels(data: List<TaskItemViewModel>) {
        listItemTaskIncomplete {
            id(modelCountBuiltSoFar)
        }
        data.filter { !it.completed }.forEach {
            listItemTask {
                id(it.id)
                title(it.title)
                completed(it.completed)
                onCheckedChanged { _, isChecked ->
                    it.onCheckedChanged(isChecked)
                }
            }
        }
        listItemTaskCompleted {
            id(modelCountBuiltSoFar)
        }
        data.filter { it.completed }.forEach {
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
