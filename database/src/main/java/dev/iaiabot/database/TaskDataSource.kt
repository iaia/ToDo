package dev.iaiabot.database

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import dev.iaiabot.database.model.TaskModel
import dev.iaiabot.entity.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface TaskDataSource {
    fun add(userId: String, task: Task)
    fun delete(userId: String, task: Task)
    suspend fun update(userId: String, task: Task, newTaskTitle: String)
    fun saveCompletedState(userId: String, taskId: String, completedState: Boolean)
    fun getAllTask(userId: String): Flow<List<Task>>
    suspend fun allIncompleteTask(userId: String): List<Task>
    suspend fun allCompletedTask(userId: String): List<Task>
}

internal class TaskDataSourceImpl(
    private val dbConfig: DatabaseConfig
) : TaskDataSource {
    // users/{user_id}/tasks/{task_id}

    private val db = dbConfig.db
    private var taskCounter = 0

    private fun collection(userId: String) = db.collection("users/${userId}/tasks")

    override fun add(userId: String, task: Task) {
        collection(userId).add(TaskModel(title = task.title, order = taskCounter + 1))
    }

    override fun delete(userId: String, task: Task) {
        collection(userId).document(task.id).delete()
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

    override fun getAllTask(userId: String): Flow<List<Task>> {
        return callbackFlow<List<TaskModel>> {
            collection(userId)
                .orderBy("order", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    val tasks = it.toObjects(TaskModel::class.java)
                    taskCounter = tasks.maxOf { it.order }
                    trySend(tasks)
                }
                .addOnFailureListener {
                    close(it)
                }
            collection(userId).addSnapshotListener { value, error ->
                if (value == null) {
                    return@addSnapshotListener
                }
                val newTasks = value.toObjects(TaskModel::class.java)
                taskCounter = newTasks.maxOf { it.order }
                trySend(newTasks)
            }
            awaitClose { }
        }
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
