package dev.iaiabot.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal interface FirebaseAuthConfig {
    val me: FirebaseUser?
    val loggedIn: Flow<Boolean>

    suspend fun login(email: String, password: String): Flow<Unit>
    suspend fun logout()
    suspend fun createUser(email: String, password: String)
}

internal class FirebaseAuthConfigImpl : FirebaseAuthConfig {
    override val me: FirebaseUser?
        get() = auth.currentUser

    private val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    @ExperimentalCoroutinesApi
    override val loggedIn: Flow<Boolean> = callbackFlow<Boolean> {
        val listener = FirebaseAuth.AuthStateListener {
            if (it.currentUser != null) {
                offer(true)
            } else {
                offer(false)
            }
        }
        auth.addAuthStateListener(listener)
        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun login(email: String, password: String) = callbackFlow<Unit> {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                offer(Unit)
            }
            .addOnFailureListener {
                close(it)
            }
        awaitClose { }
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
