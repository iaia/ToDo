package dev.iaiabot.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal interface FirebaseAuthConfig {
    val me: FirebaseUser?

    suspend fun login(email: String, password: String)
    suspend fun logout()
    suspend fun createUser(email: String, password: String)
}

internal class FirebaseAuthConfigImpl : FirebaseAuthConfig {
    override val me: FirebaseUser?
        get() = auth.currentUser

    private val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String) {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    @ExperimentalCoroutinesApi
    override suspend fun createUser(email: String, password: String) {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    sendMail(it.user)
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    private fun sendMail(user: FirebaseUser?) {
        user?.sendEmailVerification()
    }
}
