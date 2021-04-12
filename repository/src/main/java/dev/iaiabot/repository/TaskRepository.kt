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
    fun complete(taskId: String)
    suspend fun refreshTasks()
}

internal class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher,
) : TaskRepository {
    override val tasks = MutableLiveData<List<Task>>()

    private val userId: String?
        get() = userRepository.me()?.id

    override fun add(task: Task) {
        userId?.let { userId ->
            taskDao.add(userId, task)
        }
    }

    override fun complete(taskId: String) {
        userId?.let { userId ->
            taskDao.complete(userId, taskId)
        }
    }

    override suspend fun refreshTasks() {
        withContext(dispatcher) {
            userId?.let { userId ->
                tasks.postValue(taskDao.refreshTasks(userId))
            }
        }
    }
}
