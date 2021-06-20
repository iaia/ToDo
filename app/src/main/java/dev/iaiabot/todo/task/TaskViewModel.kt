package dev.iaiabot.todo.task

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.task.AddTaskUseCase
import dev.iaiabot.usecase.task.ChangeTaskUseCase
import dev.iaiabot.usecase.task.GetTasksUseCase
import dev.iaiabot.usecase.task.ToggleCompleteTaskUseCase
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver {
    abstract val allTask: LiveData<List<Task>>

    @VisibleForTesting
    abstract fun init()
    abstract fun onClickAddTask()
    abstract fun toggleComplete(task: Task)
    abstract fun onChangeTask(task: Task, newTaskTitle: String)
}

internal class TaskViewModelImpl(
    showCompletedOnly: Boolean,
    getAllTaskUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val toggleCompleteTaskUseCase: ToggleCompleteTaskUseCase,
    private val changeTaskUseCase: ChangeTaskUseCase,
) : TaskViewModel() {

    override val allTask = getAllTaskUseCase(showCompletedOnly).asLiveData()

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
