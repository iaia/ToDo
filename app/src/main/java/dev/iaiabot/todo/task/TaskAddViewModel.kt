package dev.iaiabot.todo.task

import androidx.lifecycle.MutableLiveData

interface TaskAddViewModel {
    val newTaskTitle: MutableLiveData<String>

    fun addTask()
}
