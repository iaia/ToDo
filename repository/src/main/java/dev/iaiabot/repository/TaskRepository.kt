package dev.iaiabot.repository

import dev.iaiabot.database.TaskDataSource
import dev.iaiabot.entity.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface TaskRepository {
    suspend fun add(task: Task)
    suspend fun delete(task: Task)
    suspend fun update(task: Task, newTaskTitle: String)
    fun saveCompletedState(taskId: String, newCompletedState: Boolean)
    fun allTask(): Flow<List<Task>>
    suspend fun allIncompleteTask(): List<Task>
    suspend fun allCompletedTask(): List<Task>
}

internal class TaskRepositoryImpl(
    private val taskDataSource: TaskDataSource,
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher,
) : TaskRepository {
    private val userId: String?
        get() = userRepository.me()?.id

    override suspend fun add(task: Task) {
        withContext(dispatcher) {
            userId?.let { userId ->
                taskDataSource.add(userId, task)
            }
        }
    }

    override suspend fun delete(task: Task) {
        withContext(dispatcher) {
            userId?.let { userId ->
                taskDataSource.delete(userId, task)
            }
        }
    }

    override suspend fun update(task: Task, newTaskTitle: String) {
        userId?.let { userId ->
            taskDataSource.update(userId, task, newTaskTitle)
        }
    }

    override fun saveCompletedState(taskId: String, newCompletedState: Boolean) {
        userId?.let { userId ->
            taskDataSource.saveCompletedState(userId, taskId, newCompletedState)
        }
    }

    override fun allTask(): Flow<List<Task>> {
        userId?.let { userId ->
            return taskDataSource.getAllTask(userId)
        } ?: throw Exception("TODO: user id 無いときのexception")
    }

    override suspend fun allIncompleteTask(): List<Task> {
        return withContext(dispatcher) {
            userId?.let { userId ->
                taskDataSource.allIncompleteTask(userId)
            } ?: emptyList()
        }
    }

    override suspend fun allCompletedTask(): List<Task> {
        return withContext(dispatcher) {
            userId?.let { userId ->
                taskDataSource.allCompletedTask(userId)
            } ?: emptyList()
        }
    }
}
