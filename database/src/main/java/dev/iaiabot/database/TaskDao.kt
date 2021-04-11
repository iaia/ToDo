package dev.iaiabot.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.toObjects
import dev.iaiabot.entity.Task

interface TaskDao {
    val tasks: LiveData<List<Task>>

    fun refreshTasks(userId: String)
    fun add(userId: String, task: Task)
}

internal class TaskDaoImpl(
    private val dbConfig: DatabaseConfig
) : TaskDao {
    // users/{user_id}/tasks/{task_id}

    private val db = dbConfig.db
    override val tasks = MutableLiveData<List<Task>>()

    private fun collection(userId: String) = db.collection("users/${userId}/tasks")

    override fun refreshTasks(userId: String) {
        collection(userId).get().addOnSuccessListener {
            tasks.postValue(it.toObjects())
        }
    }

    override fun add(userId: String, task: Task) {
        collection(userId).add(task)
    }
}
