package dev.iaiabot.database

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.toObjects
import dev.iaiabot.entity.Task
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface TaskDao {
    suspend fun refreshTasks(userId: String): List<Task>
    fun add(userId: String, task: Task)
}

internal class TaskDaoImpl(
    private val dbConfig: DatabaseConfig
) : TaskDao {
    // users/{user_id}/tasks/{task_id}

    private val db = dbConfig.db

    private fun collection(userId: String) = db.collection("users/${userId}/tasks")

    override suspend fun refreshTasks(userId: String): List<Task> {
        return suspendCoroutine { continuation ->
            collection(userId).get().addOnSuccessListener {
                continuation.resume(it.toObjects<TaskEntity>())
            }
        }
    }

    override fun add(userId: String, task: Task) {
        collection(userId).add(TaskEntity(title = task.title))
    }
}

data class TaskEntity(
    @DocumentId
    override val id: String = "",
    override val title: String = "",
    override val completed: Boolean = false,
    override val order: Int = 0,
) : Task
