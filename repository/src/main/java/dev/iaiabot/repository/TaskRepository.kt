package dev.iaiabot.repository

interface TaskRepository {
    fun add()
    fun restoreCompleted()
    fun complete()
    fun getAllUncompleted()
}

internal class TaskRepositoryImpl : TaskRepository {
    override fun add() {
        TODO("Not yet implemented")
    }

    override fun restoreCompleted() {
        TODO("Not yet implemented")
    }

    override fun complete() {
        TODO("Not yet implemented")
    }

    override fun getAllUncompleted() {
        TODO("Not yet implemented")
    }
}
