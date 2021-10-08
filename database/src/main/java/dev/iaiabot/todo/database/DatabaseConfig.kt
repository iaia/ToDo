package dev.iaiabot.todo.database

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal class DatabaseConfig {
     val db = Firebase.firestore
}
