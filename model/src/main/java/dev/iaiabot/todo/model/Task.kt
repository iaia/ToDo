package dev.iaiabot.todo.model

interface Task {
    val id: String
    val title: String
    val completed: Boolean
    val order: Int
}
