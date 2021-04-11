package dev.iaiabot.usecase

import dev.iaiabot.repository.TaskRepository

interface RefreshAllTasks {
    suspend operator fun invoke()
}

internal class RefreshAllTasksImpl(
    private val taskRepository: TaskRepository,
) : RefreshAllTasks {
    override suspend fun invoke() {
        taskRepository.refreshTasks()
    }
}

