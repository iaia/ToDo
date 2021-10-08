package dev.iaiabot.todo.auth.model

import dev.iaiabot.todo.model.User

internal data class UserModel(
    override val id: String
) : User
