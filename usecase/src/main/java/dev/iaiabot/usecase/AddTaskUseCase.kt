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
        taskRepository.add(TaskModel(title = title))
        return true
    }
}

data class TaskModel(
    override val id: String = "",
    override val title: String,
    override var completed: Boolean = false,
    override var order: Int = 0,
) : Task
