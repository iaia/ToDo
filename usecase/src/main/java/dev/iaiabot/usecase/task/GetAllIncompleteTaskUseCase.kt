package dev.iaiabot.usecase.task

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

interface GetAllIncompleteTaskUseCase {
    suspend operator fun invoke(): List<Task>
}

internal class GetAllIncompleteTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
) : GetAllIncompleteTaskUseCase {
    override suspend fun invoke(): List<Task> {
        return taskRepository.allIncompleteTask()
    }
}

