package dev.iaiabot.auth

import dev.iaiabot.auth.model.UserModel
import dev.iaiabot.entity.User
import kotlinx.coroutines.flow.Flow

interface UserAuth {
    val me: User?
    val alreadyLoggedIn: Flow<Boolean>

    suspend fun login(email: String, password: String)
    suspend fun logout()
    suspend fun signUp(email: String, password: String)
}

internal class UserAuthImpl(
    private val firebaseAuthConfig: FirebaseAuthConfig,
) : UserAuth {

    override val me: User?
        get() = firebaseAuthConfig.me?.let {
            UserModel(it.uid)
        }

    override val alreadyLoggedIn: Flow<Boolean> = firebaseAuthConfig.loggedIn

    override suspend fun login(email: String, password: String) {
        firebaseAuthConfig.login(email, password)
    }

    override suspend fun logout() {
        return firebaseAuthConfig.logout()
    }

    override suspend fun signUp(email: String, password: String) {
        firebaseAuthConfig.createUser(email, password)
    }
}
