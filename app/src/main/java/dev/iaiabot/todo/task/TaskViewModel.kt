package dev.iaiabot.todo.task

import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.AddTaskUseCase
import dev.iaiabot.usecase.CompleteTaskUseCase
import dev.iaiabot.usecase.GetAllCompletedTaskUseCase
import dev.iaiabot.usecase.GetAllIncompleteTaskUseCase
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver, TaskAddViewModel {
    abstract val allIncompleteTask: LiveData<List<Task>>
    abstract val allCompletedTask: LiveData<List<Task>>

    abstract fun completeTask(task: Task)
}

internal class TaskViewModelImpl(
    private val addTaskUseCase: AddTaskUseCase,
    private val getAllIncompleteTaskUseCase: GetAllIncompleteTaskUseCase,
    private val getAllCompletedTaskUseCase: GetAllCompletedTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : TaskViewModel() {

    override val newTaskTitle = MutableLiveData<String>("")
    override val allIncompleteTask = MutableLiveData<List<Task>>()
    override val allCompletedTask = MutableLiveData<List<Task>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun init() {
        refreshAllTask()
    }

    override fun addTask() {
        viewModelScope.launch {
            val success = addTaskUseCase.invoke(newTaskTitle.value)
            if (success) {
                refreshAddedTask()
            }
        }
    }

    override fun completeTask(task: Task) {
        viewModelScope.launch {
            completeTaskUseCase.invoke(task)
            refreshAllTask()
        }
    }

    override fun refreshAddedTask() {
        newTaskTitle.value = ""
        viewModelScope.launch {
            allIncompleteTask.postValue(getAllIncompleteTaskUseCase.invoke())
        }
    }

    private fun refreshAllTask() {
        viewModelScope.launch {
            allIncompleteTask.postValue(getAllIncompleteTaskUseCase.invoke())
            allCompletedTask.postValue(getAllCompletedTaskUseCase.invoke())
        }
    }
}
