package dev.iaiabot.todo.task

import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import androidx.lifecycle.LifecycleOwner
import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.entity.Task
import dev.iaiabot.todo.databinding.ListItemAddTaskBinding
import dev.iaiabot.todo.listItemAddTask
import dev.iaiabot.todo.listItemTask

class TaskController(
    private val viewModel: TaskAddViewModel,
    private val lifecycleOwner: LifecycleOwner,
) : TypedEpoxyController<List<Task>>() {
    override fun buildModels(data: List<Task>) {

        listItemAddTask {
            id(modelCountBuiltSoFar)
            viewModel(viewModel)

            onBind { _, view, _ ->
                val binding = view.dataBinding as ListItemAddTaskBinding
                binding.lifecycleOwner = lifecycleOwner
                binding.tietTitle.setOnEditorActionListener { v, actionId, event ->
                    if (actionId == IME_ACTION_DONE) {
                        viewModel.addTask()
                    }
                    return@setOnEditorActionListener true
                }
            }
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
