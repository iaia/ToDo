package dev.iaiabot.todo.auth.di

import dev.iaiabot.todo.auth.FirebaseAuthConfig
import dev.iaiabot.todo.auth.FirebaseAuthConfigImpl
import dev.iaiabot.todo.auth.UserAuth
import dev.iaiabot.todo.auth.UserAuthImpl
import org.koin.dsl.module

object Module {
    val authModule = module {
        single<FirebaseAuthConfig> { FirebaseAuthConfigImpl() }
        single<UserAuth> { UserAuthImpl(get()) }
    }
}
