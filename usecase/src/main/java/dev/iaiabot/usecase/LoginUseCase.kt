package dev.iaiabot.usecase

interface LoginUseCase : UseCase {
    operator fun invoke()
}

internal class LoginUseCaseImpl : LoginUseCase {
    override fun invoke() {
        TODO("Not yet implemented")
    }
}
