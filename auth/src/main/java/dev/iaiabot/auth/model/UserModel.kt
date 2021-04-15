package dev.iaiabot.auth.model

import dev.iaiabot.entity.User

internal data class UserModel(
    override val id: String
) : User
