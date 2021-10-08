package dev.iaiabot.todo.usecase.task

import dev.iaiabot.todo.model.Task
import dev.iaiabot.todo.repository.TaskRepository

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
