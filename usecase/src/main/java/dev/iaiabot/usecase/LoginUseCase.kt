package dev.iaiabot.usecase

interface LoginUseCase : UseCase {
    operator fun invoke(email: String?, password: String?): Boolean
}

internal class LoginUseCaseImpl : LoginUseCase {
    override fun invoke(email: String?, password: String?): Boolean {
        return true
    }
}
