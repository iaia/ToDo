package dev.iaiabot.todo.task

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.task.*
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver {
    abstract val allTask: LiveData<List<Task>>

    @VisibleForTesting
    abstract fun init()
    abstract fun onClickAddTask()
    abstract fun deleteTask(task: Task)
    abstract fun toggleComplete(task: Task)
    abstract fun onChangeTask(task: Task, newTaskTitle: String)
}

internal class TaskViewModelImpl(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val toggleCompleteTaskUseCase: ToggleCompleteTaskUseCase,
    private val changeTaskUseCase: ChangeTaskUseCase,
) : TaskViewModel() {

    override val allTask = getAllTaskUseCase().asLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun init() {
    }

    override fun onClickAddTask() {
        viewModelScope.launch {
            addTaskUseCase()
        }
    }

    override fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
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
