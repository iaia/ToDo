package dev.iaiabot.todo.task

import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.entity.Task
import dev.iaiabot.todo.databinding.ListItemTaskBinding
import dev.iaiabot.todo.listItemTask
import java.lang.ref.WeakReference

class TaskController(
    private val viewModel: TaskViewModel
) : TypedEpoxyController<List<Task>>() {

    var editModeTaskBinding: WeakReference<ListItemTaskBinding>? = null

    override fun buildModels(data: List<Task>) {
        data.sortedByDescending { it.order }.forEach { task ->
            var checked = task.completed
            listItemTask {
                id(task.id)
                title(task.title)
                editMode(false)
                onBind { _, view, _ ->
                    val binding = view.dataBinding as ListItemTaskBinding
                    binding.taskCard.isChecked = task.completed
                    binding.taskCard.setOnClickListener {
                        switchEditMode(binding)
                        viewModel.onChangeTask(task, binding.title ?: "")
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

    private fun switchEditMode(taskBinding: ListItemTaskBinding) {
        if (taskBinding.editMode == true) {
            taskBinding.editMode = false
            editModeTaskBinding = null
            return
        }
        if (editModeTaskBinding != null) {
            editModeTaskBinding?.get()?.editMode = false
        }
        taskBinding.editMode = true
        editModeTaskBinding = WeakReference(taskBinding)
    }
}
