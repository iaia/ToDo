package dev.iaiabot.auth

import dev.iaiabot.auth.model.UserModel
import dev.iaiabot.entity.User

interface UserAuth {
    val me: User?

    suspend fun login(email: String, password: String)
    suspend fun logout()
}

internal class UserAuthImpl(
    private val firebaseAuthConfig: FirebaseAuthConfig,
) : UserAuth {

    override val me: User?
        get() = firebaseAuthConfig.me?.let {
            UserModel(it.uid)
        }

    override suspend fun login(email: String, password: String) {
        firebaseAuthConfig.login(email, password)
    }

    override suspend fun logout() {
        return firebaseAuthConfig.logout()
    }
}
