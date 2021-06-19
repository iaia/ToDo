package dev.iaiabot.todo.task

import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.entity.Task
import dev.iaiabot.todo.databinding.ListItemTaskBinding
import dev.iaiabot.todo.listItemTask

class TaskController(
    private val viewModel: TaskViewModel
) : TypedEpoxyController<List<Task>>() {

    override fun buildModels(data: List<Task>) {
        data.forEach { task ->
            listItemTask {
                id(task.id)
                task(task)
                onBind { model, view, position ->
                    val binding = view.dataBinding as ListItemTaskBinding
                    binding.taskCard.isChecked = task.completed
                    binding.taskCard.setOnClickListener {
                        viewModel.toggleComplete(task)
                    }
                }
            }
        }
    }
}
