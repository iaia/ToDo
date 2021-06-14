package dev.iaiabot.usecase.task

import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

interface GetAllTaskUseCase {
    operator fun invoke(): Flow<List<Task>>
}

internal class GetAllTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
) : GetAllTaskUseCase {
    override fun invoke(): Flow<List<Task>> {
        return taskRepository.allTask()
    }
}

