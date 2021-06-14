package dev.iaiabot.repository

import dev.iaiabot.database.TaskDao
import dev.iaiabot.entity.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface TaskRepository {
    suspend fun add(task: Task)
    suspend fun update(task: Task, newTaskTitle: String)
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

    override suspend fun add(task: Task) {
        withContext(dispatcher) {
            userId?.let { userId ->
                taskDao.add(userId, task)
            }
        }
    }

    override suspend fun update(task: Task, newTaskTitle: String) {
        userId?.let { userId ->
            taskDao.update(userId, task, newTaskTitle)
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
