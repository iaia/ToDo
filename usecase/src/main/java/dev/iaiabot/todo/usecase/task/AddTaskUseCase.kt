package dev.iaiabot.todo.usecase.task

import dev.iaiabot.todo.repository.TaskRepository
import dev.iaiabot.todo.usecase.UseCase
import dev.iaiabot.todo.usecase.model.TaskModel

interface AddTaskUseCase : UseCase {
    suspend operator fun invoke()
}

internal class AddTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : AddTaskUseCase {
    override suspend fun invoke() {
        taskRepository.add(TaskModel(title = ""))
    }
}
