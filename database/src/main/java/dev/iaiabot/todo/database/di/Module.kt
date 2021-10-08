package dev.iaiabot.todo.database.di

import dev.iaiabot.todo.database.DatabaseConfig
import dev.iaiabot.todo.database.TaskDataSource
import dev.iaiabot.todo.database.TaskDataSourceImpl
import org.koin.dsl.module

object Module {
    val databaseModule = module {
        single { DatabaseConfig() }

        single<TaskDataSource> { TaskDataSourceImpl(get()) }
    }
}
