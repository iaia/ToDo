package dev.iaiabot.todo.task

import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.AddTaskUseCase
import dev.iaiabot.usecase.CompleteTaskUseCase
import dev.iaiabot.usecase.GetAllIncompleteTaskUseCase
import dev.iaiabot.usecase.RefreshAllTasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver {
    abstract val incompleteTasks: LiveData<List<Task>>

    abstract fun addTask(title: String)
    abstract fun completeTask(taskId: Int)
}

internal class TaskViewModelImpl(
    private val addTaskUseCase: AddTaskUseCase,
    private val getAllIncompleteTaskUseCase: GetAllIncompleteTaskUseCase,
    private val refreshAllTasks: RefreshAllTasks,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : TaskViewModel() {

    override var incompleteTasks: LiveData<List<Task>> = getAllIncompleteTaskUseCase.invoke()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            refreshAllTasks.invoke()
        }
    }

    override fun addTask(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase.invoke(title)
        }
    }

    override fun completeTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            completeTaskUseCase.invoke(taskId)
        }
    }
}
