package dev.iaiabot.auth

import dev.iaiabot.entity.User

interface UserAuth {
    val me: User?

    suspend fun login(email: String, password: String): Boolean
    suspend fun logout()
}

internal class UserAuthImpl(
    private val firebaseAuthConfig: FirebaseAuthConfig,
) : UserAuth {

    override val me: User?
        get() = firebaseAuthConfig.me?.let {
            UserEntity(it.uid)
        }

    override suspend fun login(email: String, password: String): Boolean {
        return firebaseAuthConfig.login(email, password)
    }

    override suspend fun logout() {
        return firebaseAuthConfig.logout()
    }
}

data class UserEntity(
    override val id: String
) : User
