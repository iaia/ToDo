package dev.iaiabot.todo.task

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.todo.model.Task
import dev.iaiabot.todo.usecase.task.AddTaskUseCase
import dev.iaiabot.todo.usecase.task.ChangeTaskUseCase
import dev.iaiabot.todo.usecase.task.GetTasksUseCase
import dev.iaiabot.todo.usecase.task.ToggleCompleteTaskUseCase
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver {
    abstract val tasks: LiveData<List<Task>>
    abstract val showAddButton: Boolean

    @VisibleForTesting
    abstract fun init()
    abstract fun onClickAddTask()
    abstract fun toggleComplete(task: Task)
    abstract fun onChangeTask(task: Task, newTaskTitle: String)
}

internal class TaskViewModelImpl(
    showCompletedOnly: Boolean,
    getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val toggleCompleteTaskUseCase: ToggleCompleteTaskUseCase,
    private val changeTaskUseCase: ChangeTaskUseCase,
) : TaskViewModel() {

    override val showAddButton: Boolean = !showCompletedOnly
    override val tasks = getTasksUseCase(showCompletedOnly).asLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun init() {
    }

    override fun onClickAddTask() {
        viewModelScope.launch {
            addTaskUseCase()
        }
    }

    override fun toggleComplete(task: Task) {
        viewModelScope.launch {
            toggleCompleteTaskUseCase(task)
        }
    }

    override fun onChangeTask(task: Task, newTaskTitle: String) {
        viewModelScope.launch {
            changeTaskUseCase(task, newTaskTitle)
        }
    }
}
