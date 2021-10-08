package dev.iaiabot.todo.task

import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.todo.bindingadapter.OnOkInSoftKeyboardListener
import dev.iaiabot.todo.databinding.ViewTaskBinding
import dev.iaiabot.todo.model.Task
import dev.iaiabot.todo.viewTask
import java.lang.ref.WeakReference

class TaskController(
    private val viewModel: TaskViewModel,
    private val hideKeyboard: () -> Unit
) : TypedEpoxyController<List<Task>>() {

    private var editModeTaskBinding: WeakReference<ViewTaskBinding>? = null

    val onOkInSoftKeyboardListener = object : OnOkInSoftKeyboardListener {
        override fun onOkInSoftKeyboard() {
            finishEdit()
        }
    }

    override fun buildModels(data: List<Task>) {
        data.sortedByDescending { it.order }.forEach { task ->
            var checked = task.completed
            viewTask {
                id(task.id)
                task(task)
                title(task.title)
                editMode(false)
                onOkInSoftKeyboardListener(onOkInSoftKeyboardListener)
                onBind { _, view, _ ->
                    val binding = view.dataBinding as ViewTaskBinding
                    binding.checked = task.completed
                    binding.taskCard.setOnClickListener {
                        switchEditMode(binding)
                    }
                    binding.taskCard.setOnLongClickListener {
                        viewModel.toggleComplete(task)
                        checked = !checked
                        binding.checked = checked
                        true
                    }
                }
            }
        }
    }

    private fun switchEditMode(taskBinding: ViewTaskBinding) {
        if (taskBinding.editMode == true) {
            finishEdit()
            return
        }
        finishEdit()
        taskBinding.editMode = true
        /*
        taskBinding.tietTitle.postDelayed(100) {
            taskBinding.tietTitle.requestFocus()
        }

         */
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
