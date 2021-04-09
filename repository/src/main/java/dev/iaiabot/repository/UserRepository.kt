package dev.iaiabot.repository

interface UserRepository {
    fun login()
    fun logout()
}

internal class UserRepositoryImpl : UserRepository {
    override fun login() {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}
