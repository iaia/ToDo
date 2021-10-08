package dev.iaiabot.todo.usecase.user

import dev.iaiabot.todo.repository.UserRepository
import dev.iaiabot.todo.usecase.UseCase

interface LogoutUseCase : UseCase {
    suspend operator fun invoke()
}

internal class LogoutUseCaseImpl(
    private val userRepository: UserRepository,
) : LogoutUseCase {
    override suspend fun invoke() {
        userRepository.logout()
    }
}
