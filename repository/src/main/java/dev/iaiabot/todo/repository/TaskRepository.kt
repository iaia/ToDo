package dev.iaiabot.todo.repository

import dev.iaiabot.todo.database.TaskDataSource
import dev.iaiabot.todo.model.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface TaskRepository {
    suspend fun add(task: Task)
    suspend fun delete(task: Task)
    suspend fun update(task: Task, newTaskTitle: String)
    fun saveCompletedState(taskId: String, newCompletedState: Boolean)
    fun getTasks(onlyCompleted: Boolean): Flow<List<Task>>
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

    override fun getTasks(onlyCompleted: Boolean): Flow<List<Task>> {
        userId?.let { userId ->
            return taskDataSource.getTasks(userId, onlyCompleted)
        } ?: throw Exception("TODO: user id 無いときのexception")
    }
}
