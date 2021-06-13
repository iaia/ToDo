package dev.iaiabot.usecase.user

import dev.iaiabot.repository.UserRepository
import dev.iaiabot.usecase.UseCase
import kotlinx.coroutines.flow.Flow

interface CheckAlreadyLoggedInUseCase : UseCase {
    operator fun invoke(): Flow<Boolean>
}

internal class CheckAlreadyLoggedInUseCaseImpl(
    private val userRepository: UserRepository
) : CheckAlreadyLoggedInUseCase {
    override fun invoke(): Flow<Boolean> = userRepository.alreadyLoggedIn
}
