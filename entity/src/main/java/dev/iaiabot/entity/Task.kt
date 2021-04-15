package dev.iaiabot.entity

interface Task {
    val id: String
    val title: String
    var completed: Boolean
    var order: Int
}
