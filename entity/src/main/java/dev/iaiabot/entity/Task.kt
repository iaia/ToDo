package dev.iaiabot.entity

interface Task {
    val id: Int
    val title: String
    val checked: Boolean
    val order: Int
}
