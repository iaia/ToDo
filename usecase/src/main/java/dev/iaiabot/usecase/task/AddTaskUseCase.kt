package dev.iaiabot.usecase.task

import dev.iaiabot.repository.TaskRepository
import dev.iaiabot.usecase.UseCase
import dev.iaiabot.usecase.model.TaskModel

interface AddTaskUseCase : UseCase {
    // Resultにする
    operator fun invoke(title: String?): Boolean
}

internal class AddTaskUseCaseImpl(
    private val taskRepository: TaskRepository
) : AddTaskUseCase {
    override fun invoke(title: String?): Boolean {
        if (title.isNullOrEmpty()) {
            return false
        }
        taskRepository.add(TaskModel(title = title))
        return true
    }
}
