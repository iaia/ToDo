package dev.iaiabot.entity

interface Task {
    val id: Int
    val title: String
    val completed: Boolean
    val order: Int
}
