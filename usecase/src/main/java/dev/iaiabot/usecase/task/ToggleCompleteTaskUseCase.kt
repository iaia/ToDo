package dev.iaiabot.usecase.task

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository
import dev.iaiabot.usecase.UseCase

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
