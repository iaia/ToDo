package dev.iaiabot.repository.di

import dev.iaiabot.auth.di.Module.authModule
import dev.iaiabot.database.di.Module.databaseModule
import dev.iaiabot.repository.TaskRepository
import dev.iaiabot.repository.TaskRepositoryImpl
import dev.iaiabot.repository.UserRepository
import dev.iaiabot.repository.UserRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object Module {
    val repositoryModule = module {
        loadKoinModules(listOf(databaseModule, authModule))

        single<TaskRepository> { TaskRepositoryImpl(get(), get(), get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }

        factory<CoroutineDispatcher> { Dispatchers.IO }
    }
}
