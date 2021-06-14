package dev.iaiabot.usecase.task

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository
import dev.iaiabot.usecase.UseCase

interface ChangeTaskUseCase : UseCase {
    suspend operator fun invoke(task: Task, newTaskTitle: String)
}

internal class ChangeTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : ChangeTaskUseCase {
    override suspend fun invoke(task: Task, newTaskTitle: String) {
        taskRepository.update(task, newTaskTitle)
    }
}
