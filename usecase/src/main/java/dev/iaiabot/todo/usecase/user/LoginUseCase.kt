package dev.iaiabot.todo.usecase.user

import dev.iaiabot.todo.repository.UserRepository
import dev.iaiabot.todo.usecase.UseCase
import kotlinx.coroutines.flow.Flow

interface LoginUseCase : UseCase {
    suspend operator fun invoke(email: String?, password: String?): Flow<Unit>
}

internal class LoginUseCaseImpl(
    private val userRepository: UserRepository
) : LoginUseCase {
    override suspend fun invoke(email: String?, password: String?): Flow<Unit> {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            throw Exception("fill email and password")
        }

        return userRepository.login(email, password)
    }
}
