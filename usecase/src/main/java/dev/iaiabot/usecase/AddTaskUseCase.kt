package dev.iaiabot.usecase

interface AddTaskUseCase : UseCase {
    operator fun invoke(title: String)
}

internal class AddTaskUseCaseImpl : AddTaskUseCase {
    override fun invoke(title: String) {
    }
}
