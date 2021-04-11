package dev.iaiabot.repository.di

import dev.iaiabot.database.di.Module.databaseModule
import dev.iaiabot.repository.TaskRepository
import dev.iaiabot.repository.TaskRepositoryImpl
import dev.iaiabot.repository.UserRepository
import dev.iaiabot.repository.UserRepositoryImpl
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object Module {
    val repositoryModule = module {
        loadKoinModules(databaseModule)

        single<TaskRepository> { TaskRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl() }
    }
}
