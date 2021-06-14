package dev.iaiabot.database.di

import dev.iaiabot.database.DatabaseConfig
import dev.iaiabot.database.TaskDataSource
import dev.iaiabot.database.TaskDataSourceImpl
import org.koin.dsl.module

object Module {
    val databaseModule = module {
        single { DatabaseConfig() }

        single<TaskDataSource> { TaskDataSourceImpl(get()) }
    }
}
