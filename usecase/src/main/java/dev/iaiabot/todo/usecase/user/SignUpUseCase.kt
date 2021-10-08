package dev.iaiabot.todo.usecase.user

import dev.iaiabot.todo.repository.UserRepository
import dev.iaiabot.todo.usecase.UseCase

interface SignUpUseCase : UseCase {
    suspend operator fun invoke(email: String?, password: String?)
}

internal class SignUpUseCaseImpl(
    private val userRepository: UserRepository,
) : SignUpUseCase {

    override suspend fun invoke(email: String?, password: String?) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return
        }

        userRepository.signUp(email, password)
    }
}

