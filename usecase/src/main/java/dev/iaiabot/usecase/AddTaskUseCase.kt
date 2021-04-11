package dev.iaiabot.usecase

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

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
        taskRepository.add(TaskModel(title))
        return true
    }
}

data class TaskModel(
    override val title: String,
    override val id: String = "",
    override val order: Int = 0,
    override val completed: Boolean = false,
) : Task
