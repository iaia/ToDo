package dev.iaiabot.usecase

interface CompleteTaskUseCase : UseCase {
    operator fun invoke()
}

internal class CompleteTaskUseCaseImpl : CompleteTaskUseCase {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}
