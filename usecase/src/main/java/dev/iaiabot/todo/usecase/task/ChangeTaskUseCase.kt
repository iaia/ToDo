package dev.iaiabot.todo.usecase.task

import dev.iaiabot.todo.model.Task
import dev.iaiabot.todo.repository.TaskRepository
import dev.iaiabot.todo.usecase.UseCase
import kotlinx.coroutines.*

interface ChangeTaskUseCase : UseCase {
    suspend operator fun invoke(task: Task, newTaskTitle: String)
}

internal class ChangeTaskUseCaseImpl(
    private val taskRepository: TaskRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ChangeTaskUseCase {
    private var job: Job? = null

    override suspend fun invoke(task: Task, newTaskTitle: String) {
        job?.cancelAndJoin()
        coroutineScope {
            job = launch {
                delay(1000)
                taskRepository.update(task, newTaskTitle)
            }
        }
    }
}
