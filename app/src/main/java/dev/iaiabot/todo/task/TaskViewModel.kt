package dev.iaiabot.todo.task

import androidx.lifecycle.*
import dev.iaiabot.entity.Task
import dev.iaiabot.usecase.AddTaskUseCase
import dev.iaiabot.usecase.CompleteTaskUseCase
import dev.iaiabot.usecase.GetAllTaskUseCase
import dev.iaiabot.usecase.RefreshAllTasks
import kotlinx.coroutines.launch

abstract class TaskViewModel : ViewModel(), LifecycleObserver, TaskAddViewModel {
    // TODO: 完了・未完了によらずとりあえず全部取ってくるので名前変える
    abstract val allTask: LiveData<List<Task>>

    abstract fun completeTask(task: Task)
}

internal class TaskViewModelImpl(
    private val addTaskUseCase: AddTaskUseCase,
    getAllTaskUseCase: GetAllTaskUseCase,
    private val refreshAllTasks: RefreshAllTasks,
    private val completeTaskUseCase: CompleteTaskUseCase,
) : TaskViewModel() {

    override val newTaskTitle = MutableLiveData<String>("")
    override var allTask: LiveData<List<Task>> = getAllTaskUseCase.invoke()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun init() {
        viewModelScope.launch {
            refreshAllTasks.invoke()
        }
    }

    override fun addTask() {
        viewModelScope.launch {
            val success = addTaskUseCase.invoke(newTaskTitle.value)
            if (success) {
                newTaskTitle.postValue("")
                refreshAllTasks.invoke()
            }
        }
    }

    override fun completeTask(task: Task) {
        viewModelScope.launch {
            completeTaskUseCase.invoke(task)
        }
    }
}
