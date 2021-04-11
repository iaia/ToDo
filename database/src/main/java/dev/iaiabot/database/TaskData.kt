package dev.iaiabot.database

import com.google.firebase.firestore.ktx.toObjects
import dev.iaiabot.entity.Task
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TaskData(
    private val dbConfig: DatabaseConfig
) {
    // users/{user_id}/tasks/{task_id}

    private val db = dbConfig.db

    private fun collection(userId: String) = db.collection("users/${userId}/tasks")

    suspend fun getAllTask(userId: String): List<Task> {
        return suspendCoroutine { continuation ->
            collection(userId).get().addOnSuccessListener {
                continuation.resume(it.toObjects<Task>())
            }
        }
    }

    fun add(userId: String, task: Task) {
        collection(userId).add(task)
    }
}
