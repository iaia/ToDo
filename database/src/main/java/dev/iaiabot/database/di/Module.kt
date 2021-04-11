package dev.iaiabot.database.di

import org.koin.dsl.module

object Module {
    val databaseModule = module {
        single { DatabaseConfig() }
    }
}
