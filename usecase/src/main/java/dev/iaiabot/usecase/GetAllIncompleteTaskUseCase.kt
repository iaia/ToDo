package dev.iaiabot.usecase

import androidx.lifecycle.LiveData
import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository

interface GetAllIncompleteTaskUseCase {
    suspend operator fun invoke(): LiveData<List<Task>>
}

internal class GetAllIncompleteTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
) : GetAllIncompleteTaskUseCase {
    override suspend fun invoke(): LiveData<List<Task>> {
        return taskRepository.getAllUncompleted()
    }
}

