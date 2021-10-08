package dev.iaiabot.todo.usecase.task

import dev.iaiabot.todo.model.Task
import dev.iaiabot.todo.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

interface GetTasksUseCase {
    operator fun invoke(onlyCompleted: Boolean): Flow<List<Task>>
}

internal class GetTasksUseCaseImpl(
    private val taskRepository: TaskRepository,
) : GetTasksUseCase {
    override fun invoke(onlyCompleted: Boolean): Flow<List<Task>> {
        return taskRepository.getTasks(onlyCompleted)
    }
}

