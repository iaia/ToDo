package dev.iaiabot.usecase.model

import dev.iaiabot.entity.Task

internal data class TaskModel(
    override val id: String = "",
    override val title: String,
    override val completed: Boolean = false,
    override val order: Int = 0,
) : Task

