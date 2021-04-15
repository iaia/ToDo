package dev.iaiabot.usecase.user

import dev.iaiabot.repository.UserRepository
import dev.iaiabot.usecase.UseCase

interface CheckAlreadyLoggedInUseCase : UseCase {
    suspend operator fun invoke(): Boolean
}

internal class CheckAlreadyLoggedInUseCaseImpl(
    private val userRepository: UserRepository
) : CheckAlreadyLoggedInUseCase {
    override suspend fun invoke(): Boolean {
        return alreadyLoggedIn()
    }

    private fun alreadyLoggedIn() = userRepository.me() != null
}
