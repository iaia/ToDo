package dev.iaiabot.todo.task

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.task.AddTaskUseCase
import dev.iaiabot.usecase.task.GetAllCompletedTaskUseCase
import dev.iaiabot.usecase.task.GetAllIncompleteTaskUseCase
import dev.iaiabot.usecase.task.ToggleCompleteTaskUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver {
    abstract val ableAddTask: LiveData<Boolean>
    abstract val allTask: LiveData<List<TaskItemViewModel>>
    abstract val newTaskTitle: MutableLiveData<String>

    @VisibleForTesting
    abstract fun init()
    abstract fun addTask()
    abstract fun onClickAddTask()
}

internal class TaskViewModelImpl(
    private val addTaskUseCase: AddTaskUseCase,
    private val getAllIncompleteTaskUseCase: GetAllIncompleteTaskUseCase,
    private val getAllCompletedTaskUseCase: GetAllCompletedTaskUseCase,
    private val toggleCompleteTaskUseCase: ToggleCompleteTaskUseCase,
) : TaskViewModel() {

    override val ableAddTask = MutableLiveData<Boolean>(false)
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

    override fun onClickAddTask() {
        ableAddTask.value?.let {
            ableAddTask.postValue(!it)
        }
    }

    private fun refreshAddedTask() {
        newTaskTitle.value = ""
        refreshAllTask()
    }

    private fun onCheckedChanged(task: Task, checked: Boolean) {
        viewModelScope.launch {
            toggleCompleteTaskUseCase.invoke(task)
            refreshTaskJob?.cancel()
            refreshTaskJob = launch {
                delay(1000)
                // TODO: ローカル情報だけで済むのでどうにかする
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
