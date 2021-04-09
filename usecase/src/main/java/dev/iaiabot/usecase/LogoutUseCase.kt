package dev.iaiabot.usecase

interface LogoutUseCase : UseCase {
    operator fun invoke()
}

internal class LogoutUseCaseImpl : LoginUseCase {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}
