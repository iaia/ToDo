package dev.iaiabot.entity

interface Task {
    val id: String
    val title: String
    val completed: Boolean
    val order: Int
}
