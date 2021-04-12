package dev.iaiabot.todo.task

import androidx.lifecycle.MutableLiveData

interface NewTaskViewModel {
    val newTaskTitle: MutableLiveData<String>

    fun addTask()
    fun refreshAddedTask()
}
