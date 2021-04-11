package dev.iaiabot.usecase

import dev.iaiabot.repository.UserRepository

interface LoginUseCase : UseCase {
    // TODO: エラー出したいのでresultにする
    suspend operator fun invoke(email: String?, password: String?): Boolean
}

internal class LoginUseCaseImpl(
    private val userRepository: UserRepository
) : LoginUseCase {
    override suspend fun invoke(email: String?, password: String?): Boolean {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return false
        }

        if (alreadyExists()) {
            return true
        }
        userRepository.login(email, password)

        return alreadyExists()
    }

    private fun alreadyExists() = userRepository.me() != null
}
