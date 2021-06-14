package dev.iaiabot.database

import com.google.firebase.firestore.ktx.toObjects
import dev.iaiabot.database.model.TaskModel
import dev.iaiabot.entity.Task
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface TaskDataSource {
    fun add(userId: String, task: Task)
    suspend fun update(userId: String, task: Task, newTaskTitle: String)
    fun saveCompletedState(userId: String, taskId: String, completedState: Boolean)
    suspend fun allIncompleteTask(userId: String): List<Task>
    suspend fun allCompletedTask(userId: String): List<Task>
}

internal class TaskDataSourceImpl(
    private val dbConfig: DatabaseConfig
) : TaskDataSource {
    // users/{user_id}/tasks/{task_id}

    private val db = dbConfig.db

    private fun collection(userId: String) = db.collection("users/${userId}/tasks")

    override fun add(userId: String, task: Task) {
        collection(userId).add(TaskModel(title = task.title))
    }

    override suspend fun update(userId: String, task: Task, newTaskTitle: String) {
        collection(userId)
            .document(task.id)
            .update("title", newTaskTitle)
    }

    override fun saveCompletedState(userId: String, taskId: String, completedState: Boolean) {
        collection(userId)
            .document(taskId)
            .update("completed", completedState)
    }

    override suspend fun allIncompleteTask(userId: String): List<Task> {
        return suspendCoroutine { continuation ->
            collection(userId).whereEqualTo("completed", false).get().addOnSuccessListener {
                continuation.resume(it.toObjects<TaskModel>())
            }
        }
    }

    override suspend fun allCompletedTask(userId: String): List<Task> {
        return suspendCoroutine { continuation ->
            collection(userId).whereEqualTo("completed", true).get().addOnSuccessListener {
                continuation.resume(it.toObjects<TaskModel>())
            }
        }
    }
}
