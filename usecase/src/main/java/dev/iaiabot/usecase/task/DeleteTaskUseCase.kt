package dev.iaiabot.usecase.task

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

interface DeleteTaskUseCase {
    suspend operator fun invoke(task: Task)
}

internal class DeleteTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : DeleteTaskUseCase {

    override suspend fun invoke(task: Task) {
        taskRepository.delete(task)
    }
}
