package dev.iaiabot.usecase.task

import dev.iaiabot.repository.TaskRepository
import dev.iaiabot.usecase.UseCase
import dev.iaiabot.usecase.model.TaskModel

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
