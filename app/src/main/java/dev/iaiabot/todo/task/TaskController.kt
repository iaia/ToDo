package dev.iaiabot.todo.task

import androidx.core.view.postDelayed
import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.entity.Task
import dev.iaiabot.todo.bindingadapter.OnOkInSoftKeyboardListener
import dev.iaiabot.todo.databinding.ListItemTaskBinding
import dev.iaiabot.todo.listItemTask
import java.lang.ref.WeakReference

class TaskController(
    private val viewModel: TaskViewModel,
    private val hideKeyboard: () -> Unit
) : TypedEpoxyController<List<Task>>() {

    var editModeTaskBinding: WeakReference<ListItemTaskBinding>? = null

    val onOkInSoftKeyboardListener = object : OnOkInSoftKeyboardListener {
        override fun onOkInSoftKeyboard() {
            finishEdit()
        }
    }

    override fun buildModels(data: List<Task>) {
        data.sortedByDescending { it.order }.forEach { task ->
            var checked = task.completed
            listItemTask {
                id(task.id)
                task(task)
                title(task.title)
                editMode(false)
                onOkInSoftKeyboardListener(onOkInSoftKeyboardListener)
                onBind { _, view, _ ->
                    val binding = view.dataBinding as ListItemTaskBinding
                    binding.taskCard.isChecked = task.completed
                    binding.taskCard.setOnClickListener {
                        switchEditMode(binding)
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
            finishEdit()
            return
        }
        finishEdit()
        taskBinding.editMode = true
        taskBinding.tietTitle.postDelayed(100) {
            taskBinding.tietTitle.requestFocus()
        }
        editModeTaskBinding = WeakReference(taskBinding)
    }

    private fun finishEdit() {
        editModeTaskBinding?.get()?.editMode = false
        val title = editModeTaskBinding?.get()?.title
        editModeTaskBinding?.get()?.task?.let {
            viewModel.onChangeTask(it, title ?: "")
        }
        editModeTaskBinding = null
        hideKeyboard.invoke()
    }
}
