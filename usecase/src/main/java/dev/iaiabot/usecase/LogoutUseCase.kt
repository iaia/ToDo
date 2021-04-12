package dev.iaiabot.usecase

interface LogoutUseCase : UseCase {
    operator fun invoke()
}

internal class LogoutUseCaseImpl : LogoutUseCase {
    override fun invoke() {
    }
}
