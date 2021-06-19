package dev.iaiabot.todo.task

import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.entity.Task
import dev.iaiabot.todo.listItemTask

class TaskController() : TypedEpoxyController<List<Task>>() {

    override fun buildModels(data: List<Task>) {
        data.forEach { task ->
            listItemTask {
                id(task.id)
                task(task)
            }
        }
    }
}
