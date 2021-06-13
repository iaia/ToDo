package dev.iaiabot.usecase.task

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

interface GetAllTaskUseCase {
    suspend operator fun invoke(): List<Task>
}

internal class GetAllTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
) : GetAllTaskUseCase {
    override suspend fun invoke(): List<Task> {
        return taskRepository.allIncompleteTask() + taskRepository.allCompletedTask()
    }
}

