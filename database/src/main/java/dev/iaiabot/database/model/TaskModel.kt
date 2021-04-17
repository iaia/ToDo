package dev.iaiabot.database.model

import com.google.firebase.firestore.DocumentId
import dev.iaiabot.entity.Task

internal data class TaskModel(
    @DocumentId
    override val id: String = "",
    override val title: String = "",
    override val completed: Boolean = false,
    override val order: Int = 0,
) : Task
