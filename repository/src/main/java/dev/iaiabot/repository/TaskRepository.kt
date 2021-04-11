package dev.iaiabot.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.iaiabot.database.TaskDao
import dev.iaiabot.entity.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface TaskRepository {
    val tasks: LiveData<List<Task>>

    fun add(task: Task)
    fun restoreCompleted()
    fun complete()
    suspend fun refreshTasks()
}

internal class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val dispatcher: CoroutineDispatcher,
) : TaskRepository {
    override val tasks = MutableLiveData<List<Task>>()

    // TODO: 後でどうにかする
    private val userId = "437IhnAX77TGoxHcqd1c"

    override fun add(task: Task) {
        taskDao.add(userId, task)
    }

    override fun restoreCompleted() {
        TODO("Not yet implemented")
    }

    override fun complete() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTasks() {
        withContext(dispatcher) {
            tasks.postValue(taskDao.refreshTasks(userId))
        }
    }
}
