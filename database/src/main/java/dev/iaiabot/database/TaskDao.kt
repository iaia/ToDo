package dev.iaiabot.database

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.toObjects
import dev.iaiabot.entity.Task
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface TaskDao {
    fun add(userId: String, task: Task)
    fun complete(userId: String, taskId: String)
    suspend fun allIncompleteTask(userId: String): List<Task>
    suspend fun allCompletedTask(userId: String): List<Task>
}

internal class TaskDaoImpl(
    private val dbConfig: DatabaseConfig
) : TaskDao {
    // users/{user_id}/tasks/{task_id}

    private val db = dbConfig.db

    private fun collection(userId: String) = db.collection("users/${userId}/tasks")

    override fun add(userId: String, task: Task) {
        collection(userId).add(TaskEntity(title = task.title))
    }

    override fun complete(userId: String, taskId: String) {
        collection(userId)
            .document(taskId)
            .update("completed", true)
    }

    override suspend fun allIncompleteTask(userId: String): List<Task> {
        return suspendCoroutine { continuation ->
            collection(userId).whereEqualTo("completed", false).get().addOnSuccessListener {
                continuation.resume(it.toObjects<TaskEntity>())
            }
        }
    }

    override suspend fun allCompletedTask(userId: String): List<Task> {
        return suspendCoroutine { continuation ->
            collection(userId).whereEqualTo("completed", true).get().addOnSuccessListener {
                continuation.resume(it.toObjects<TaskEntity>())
            }
        }
    }
}

data class TaskEntity(
    @DocumentId
    override val id: String = "",
    override val title: String = "",
    override val completed: Boolean = false,
    override val order: Int = 0,
) : Task
