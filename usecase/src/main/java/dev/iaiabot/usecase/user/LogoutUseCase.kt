package dev.iaiabot.usecase.user

import dev.iaiabot.repository.UserRepository
import dev.iaiabot.usecase.UseCase

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
