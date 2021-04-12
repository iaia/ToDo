package dev.iaiabot.todo.task

import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.AddTaskUseCase
import dev.iaiabot.usecase.CompleteTaskUseCase
import dev.iaiabot.usecase.GetAllCompletedTaskUseCase
import dev.iaiabot.usecase.GetAllIncompleteTaskUseCase
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver, TaskAddViewModel {
    abstract val allIncompleteTask: LiveData<List<TaskItemViewModel>>
    abstract val allCompletedTask: LiveData<List<TaskItemViewModel>>
}

internal class TaskViewModelImpl(
    private val addTaskUseCase: AddTaskUseCase,
    private val getAllIncompleteTaskUseCase: GetAllIncompleteTaskUseCase,
    private val getAllCompletedTaskUseCase: GetAllCompletedTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : TaskViewModel() {

    override val newTaskTitle = MutableLiveData<String>("")
    override val allIncompleteTask = MutableLiveData<List<TaskItemViewModel>>()
    override val allCompletedTask = MutableLiveData<List<TaskItemViewModel>>()

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

    override fun refreshAddedTask() {
        newTaskTitle.value = ""
        refreshIncompleteTask()
    }

    private fun onCheckedChanged(task: Task, checked: Boolean) {
        viewModelScope.launch {
            if (checked) {
                completeTaskUseCase.invoke(task)
            }
            refreshAllTask()
        }
    }

    private fun refreshAllTask() {
        refreshIncompleteTask()
        refreshCompletedTask()
    }

    private fun refreshIncompleteTask() {
        viewModelScope.launch {
            allIncompleteTask.postValue(
                getAllIncompleteTaskUseCase.invoke().map {
                    TaskItemViewModelImpl(it, ::onCheckedChanged)
                }
            )
        }
    }

    private fun refreshCompletedTask() {
        viewModelScope.launch {
            allCompletedTask.postValue(
                getAllCompletedTaskUseCase.invoke().map {
                    TaskItemViewModelImpl(it, ::onCheckedChanged)
                }
            )
        }
    }
}
