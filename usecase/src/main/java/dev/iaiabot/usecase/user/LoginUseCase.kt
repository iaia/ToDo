package dev.iaiabot.usecase.user

import dev.iaiabot.repository.UserRepository
import dev.iaiabot.usecase.UseCase

interface LoginUseCase : UseCase {
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
