package dev.iaiabot.usecase

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

interface GetAllCompletedTaskUseCase {
    suspend operator fun invoke(): List<Task>
}

internal class GetAllCompletedTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
) : GetAllCompletedTaskUseCase {
    override suspend fun invoke(): List<Task> {
        return taskRepository.allCompletedTask()
    }
}
