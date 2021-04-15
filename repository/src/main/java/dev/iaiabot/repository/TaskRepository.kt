package dev.iaiabot.repository

import dev.iaiabot.database.TaskDao
import dev.iaiabot.entity.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface TaskRepository {
    fun add(task: Task)
    fun saveCompletedState(taskId: String, newCompletedState: Boolean)
    suspend fun allIncompleteTask(): List<Task>
    suspend fun allCompletedTask(): List<Task>
}

internal class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher,
) : TaskRepository {
    private val userId: String?
        get() = userRepository.me()?.id

    override fun add(task: Task) {
        userId?.let { userId ->
            taskDao.add(userId, task)
        }
    }

    override fun saveCompletedState(taskId: String, newCompletedState: Boolean) {
        userId?.let { userId ->
            taskDao.saveCompletedState(userId, taskId, newCompletedState)
        }
    }

    override suspend fun allIncompleteTask(): List<Task> {
        return withContext(dispatcher) {
            userId?.let { userId ->
                taskDao.allIncompleteTask(userId)
            } ?: emptyList()
        }
    }

    override suspend fun allCompletedTask(): List<Task> {
        return withContext(dispatcher) {
            userId?.let { userId ->
                taskDao.allCompletedTask(userId)
            } ?: emptyList()
        }
    }
}
