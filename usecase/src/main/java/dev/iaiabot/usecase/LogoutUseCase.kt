package dev.iaiabot.usecase

import dev.iaiabot.repository.UserRepository

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
