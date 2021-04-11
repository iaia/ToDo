package dev.iaiabot.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal interface FirebaseAuthConfig {
    val me: FirebaseUser?

    /**
     * TODO: エラーメッセージ表示したいのでresultにする
     */
    suspend fun login(email: String, password: String): Boolean
}

internal class FirebaseAuthConfigImpl : FirebaseAuthConfig {
    private lateinit var auth: FirebaseAuth
    override val me: FirebaseUser?
        get() = auth.currentUser

    init {
        // auth = Firebase.auth
    }

    override suspend fun login(email: String, password: String): Boolean {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(it.user != null)
                }
        }
    }
}
