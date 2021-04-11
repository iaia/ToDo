package dev.iaiabot.usecase

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

interface CompleteTaskUseCase : UseCase {
    operator fun invoke(task: Task)
}

internal class CompleteTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
) : CompleteTaskUseCase {
    override fun invoke(task: Task) {
        taskRepository.complete(task.id)
    }
}
