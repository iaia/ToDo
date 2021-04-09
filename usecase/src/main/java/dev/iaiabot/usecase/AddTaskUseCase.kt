package dev.iaiabot.usecase

interface AddTaskUseCase : UseCase {
    operator fun invoke()
}

internal class AddTaskUseCaseImpl : AddTaskUseCase {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}
