package dev.iaiabot.todo.task

import androidx.lifecycle.LifecycleOwner
import com.airbnb.epoxy.TypedEpoxyController
import dev.iaiabot.entity.Task
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
                view.dataBinding.lifecycleOwner = lifecycleOwner
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
