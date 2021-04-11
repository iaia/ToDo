package dev.iaiabot.repository

import androidx.lifecycle.LiveData
import dev.iaiabot.database.TaskDao
import dev.iaiabot.entity.Task

interface TaskRepository {
    fun add(task: Task)
    fun restoreCompleted()
    fun complete()
    suspend fun getAllUncompleted(): LiveData<List<Task>>
}

internal class TaskRepositoryImpl(
    private val taskDao: TaskDao,
) : TaskRepository {
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

    override suspend fun getAllUncompleted(): LiveData<List<Task>> {
        taskDao.refreshTasks(userId)
        return taskDao.tasks
    }
}
