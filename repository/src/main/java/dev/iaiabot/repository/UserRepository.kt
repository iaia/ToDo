package dev.iaiabot.repository

import dev.iaiabot.auth.UserAuth
import dev.iaiabot.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface UserRepository {
    fun me(): User?
    suspend fun login(email: String, password: String)
    suspend fun logout()
    suspend fun signUp(email: String, password: String)
}

internal class UserRepositoryImpl(
    private val userAuth: UserAuth,
    private val dispatcher: CoroutineDispatcher,
) : UserRepository {
    override fun me(): User? {
        return userAuth.me
    }

    override suspend fun login(email: String, password: String) {
        withContext(dispatcher) {
            if (me() != null) {
                return@withContext
            }
            userAuth.login(email, password)
        }
    }

    override suspend fun logout() {
        withContext(dispatcher) {
            userAuth.logout()
        }
    }

    override suspend fun signUp(email: String, password: String) {
        userAuth.signUp(email, password)
    }
}
