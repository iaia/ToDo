package dev.iaiabot.usecase

interface AddTaskUseCase : UseCase {
    // Resultにする
    operator fun invoke(title: String?): Boolean
}

internal class AddTaskUseCaseImpl : AddTaskUseCase {
    override fun invoke(title: String?): Boolean {
        return true
    }
}
