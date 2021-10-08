package dev.iaiabot.todo.usecase.task

import dev.iaiabot.todo.model.Task
import dev.iaiabot.todo.repository.TaskRepository
import dev.iaiabot.todo.usecase.UseCase

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
