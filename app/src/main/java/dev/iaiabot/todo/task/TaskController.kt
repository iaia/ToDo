package dev.iaiabot.todo.task

import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.entity.Task
import dev.iaiabot.todo.databinding.ListItemTaskBinding
import dev.iaiabot.todo.listItemTask

class TaskController(
    private val viewModel: TaskViewModel
) : TypedEpoxyController<List<Task>>() {

    override fun buildModels(data: List<Task>) {
        data.sortedByDescending { it.order }.forEach { task ->
            var checked = task.completed
            listItemTask {
                id(task.id)
                task(task)
                editMode(false)
                onBind { model, view, position ->
                    val binding = view.dataBinding as ListItemTaskBinding
                    binding.taskCard.isChecked = task.completed
                    binding.taskCard.setOnClickListener {
                        binding.editMode = !(binding.editMode ?: false)
                    }
                    binding.taskCard.setOnLongClickListener {
                        viewModel.toggleComplete(task)
                        checked = !checked
                        binding.taskCard.isChecked = checked
                        true
                    }
                }
            }
        }
    }
}
