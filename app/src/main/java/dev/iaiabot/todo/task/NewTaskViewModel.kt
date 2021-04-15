package dev.iaiabot.todo.task

import androidx.lifecycle.MutableLiveData

interface NewTaskViewModel {
    val newTaskTitle: MutableLiveData<String>

    // TODO: キーボードからフォーカス外れたときにaddTaskするようにする
    fun addTask()
    fun refreshAddedTask()
}
