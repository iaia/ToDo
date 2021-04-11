package dev.iaiabot.database.di

import dev.iaiabot.database.DatabaseConfig
import dev.iaiabot.database.TaskDao
import dev.iaiabot.database.TaskDaoImpl
import org.koin.dsl.module

object Module {
    val databaseModule = module {
        single { DatabaseConfig() }

        single<TaskDao> { TaskDaoImpl(get()) }
    }
}
