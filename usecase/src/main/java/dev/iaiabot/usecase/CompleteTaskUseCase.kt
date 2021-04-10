package dev.iaiabot.usecase

interface CompleteTaskUseCase : UseCase {
    operator fun invoke(taskId: Int)
}

internal class CompleteTaskUseCaseImpl : CompleteTaskUseCase {
    override fun invoke(taskId: Int) {
    }
}
