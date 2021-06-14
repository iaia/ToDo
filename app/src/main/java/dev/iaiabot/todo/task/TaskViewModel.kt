package dev.iaiabot.todo.task

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.task.AddTaskUseCase
import dev.iaiabot.usecase.task.GetAllTaskUseCase
import dev.iaiabot.usecase.task.ToggleCompleteTaskUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver {
    abstract val ableAddTask: LiveData<Boolean>
    abstract val allTask: LiveData<List<Task>>

    @VisibleForTesting
    abstract fun init()
    abstract fun addTask()
    abstract fun onClickAddTask()
    abstract fun toggleComplete(task: Task)
    abstract fun onChangeTask(id: String, taskTitle: String)
}

internal class TaskViewModelImpl(
    private val addTaskUseCase: AddTaskUseCase,
    private val getAllTaskUseCase: GetAllTaskUseCase,
    private val toggleCompleteTaskUseCase: ToggleCompleteTaskUseCase,
) : TaskViewModel() {

    override val ableAddTask = MutableLiveData<Boolean>(false)
    override val allTask = MutableLiveData<List<Task>>()

    private var refreshTaskJob: Job? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun init() {
        refreshAllTask()
    }

    override fun addTask() {
        viewModelScope.launch {
            addTaskUseCase()
        }
    }

    override fun onClickAddTask() {
        ableAddTask.value?.let {
            if (it) {
                addTask()
            }
            ableAddTask.postValue(!it)
        }
    }

    override fun toggleComplete(task: Task) {
        viewModelScope.launch {
            toggleCompleteTaskUseCase(task)
            refreshTaskJob?.cancel()
            refreshTaskJob = launch {
                delay(1000)
                // TODO: ローカル情報だけで済むのでどうにかする
                refreshAllTask()
            }
        }
    }

    override fun onChangeTask(id: String, taskTitle: String) {
        // todo
    }

    private fun refreshAllTask() {
        viewModelScope.launch {
            allTask.postValue(getAllTaskUseCase())
        }
    }
}
