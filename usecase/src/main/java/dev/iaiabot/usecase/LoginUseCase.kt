package dev.iaiabot.usecase

import dev.iaiabot.repository.UserRepository

interface LoginUseCase : UseCase {
    // TODO: エラー出したいのでresultにする
    suspend operator fun invoke(email: String?, password: String?)
}

internal class LoginUseCaseImpl(
    private val userRepository: UserRepository
) : LoginUseCase {
    override suspend fun invoke(email: String?, password: String?) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return
        }

        userRepository.login(email, password)
    }
}
