package dev.iaiabot.auth.di

import dev.iaiabot.auth.FirebaseAuthConfig
import dev.iaiabot.auth.FirebaseAuthConfigImpl
import dev.iaiabot.auth.UserAuth
import dev.iaiabot.auth.UserAuthImpl
import org.koin.dsl.module

object Module {
    val authModule = module {
        single<FirebaseAuthConfig> { FirebaseAuthConfigImpl() }
        single<UserAuth> { UserAuthImpl(get()) }
    }
}
