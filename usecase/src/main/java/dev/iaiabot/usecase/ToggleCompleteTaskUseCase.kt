package dev.iaiabot.usecase

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

interface ToggleCompleteTaskUseCase : UseCase {
    operator fun invoke(task: Task)
}

internal class ToggleCompleteTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
) : ToggleCompleteTaskUseCase {
    override fun invoke(task: Task) {
        taskRepository.saveCompletedState(task.id, !task.completed)
    }
}
