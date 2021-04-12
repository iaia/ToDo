package dev.iaiabot.usecase

import androidx.lifecycle.LiveData
import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

interface GetAllTaskUseCase {
    operator fun invoke(): LiveData<List<Task>>
}

internal class GetAllTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
) : GetAllTaskUseCase {
    override fun invoke(): LiveData<List<Task>> {
        return taskRepository.tasks
    }
}

