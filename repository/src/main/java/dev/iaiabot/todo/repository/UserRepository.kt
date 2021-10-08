package dev.iaiabot.todo.repository

import dev.iaiabot.todo.auth.UserAuth
import dev.iaiabot.todo.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface UserRepository {
    val alreadyLoggedIn: Flow<Boolean>

    fun me(): User?
    suspend fun login(email: String, password: String): Flow<Unit>
    suspend fun logout()
    suspend fun signUp(email: String, password: String)
}

internal class UserRepositoryImpl(
    private val userAuth: UserAuth,
    private val dispatcher: CoroutineDispatcher,
) : UserRepository {

    override val alreadyLoggedIn: Flow<Boolean> = userAuth.alreadyLoggedIn

    override fun me(): User? {
        return userAuth.me
    }

    override suspend fun login(email: String, password: String): Flow<Unit> {
        return withContext(dispatcher) {
            if (me() != null) {
                throw Exception("already logged in") // TODO: ログイン済みexception作る
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
