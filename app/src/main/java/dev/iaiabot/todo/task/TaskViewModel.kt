package dev.iaiabot.todo.task

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.AddTaskUseCase
import dev.iaiabot.usecase.CompleteTaskUseCase
import dev.iaiabot.usecase.GetAllCompletedTaskUseCase
import dev.iaiabot.usecase.GetAllIncompleteTaskUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver, NewTaskViewModel {
    abstract val allTask: LiveData<List<TaskItemViewModel>>

    @VisibleForTesting
    abstract fun init()
}

internal class TaskViewModelImpl(
    private val addTaskUseCase: AddTaskUseCase,
    private val getAllIncompleteTaskUseCase: GetAllIncompleteTaskUseCase,
    private val getAllCompletedTaskUseCase: GetAllCompletedTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : TaskViewModel() {

    override val newTaskTitle = MutableLiveData<String>("")
    override val allTask = MutableLiveData<List<TaskItemViewModel>>()

    private var refreshTaskJob: Job? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun init() {
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
        this.refreshAllTask()
    }

    private fun onCheckedChanged(task: Task, checked: Boolean) {
        viewModelScope.launch {
            if (checked) {
                completeTaskUseCase.invoke(task)
            }
            refreshTaskJob?.cancel()
            refreshTaskJob = launch {
                delay(2000) // TODO: 要調整
                refreshAllTask()
            }
        }
    }

    private fun refreshAllTask() {
        viewModelScope.launch {
            val incompleteTasks = async {
                getAllIncompleteTaskUseCase.invoke().map {
                    TaskItemViewModelImpl(it, ::onCheckedChanged)
                }
            }
            val completedTasks = async {
                getAllCompletedTaskUseCase.invoke().map {
                    TaskItemViewModelImpl(it, ::onCheckedChanged)
                }
            }
            allTask.postValue(
                incompleteTasks.await() + completedTasks.await()
            )
        }
    }
}
