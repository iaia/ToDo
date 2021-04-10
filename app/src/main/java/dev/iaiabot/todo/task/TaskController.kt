package dev.iaiabot.todo.task

import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.entity.Task
import dev.iaiabot.todo.listItemAddTask
import dev.iaiabot.todo.listItemTask

class TaskController : TypedEpoxyController<List<Task>>() {
    override fun buildModels(data: List<Task>) {

        listItemAddTask {
            id(modelCountBuiltSoFar)
        }

        data.forEach {
            listItemTask {
                id(it.id)
                title(it.title)
                completed(it.completed)
            }
        }
    }
}
