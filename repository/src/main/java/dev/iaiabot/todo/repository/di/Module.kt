package dev.iaiabot.todo.repository.di

import dev.iaiabot.todo.auth.di.Module.authModule
import dev.iaiabot.todo.database.di.Module.databaseModule
import dev.iaiabot.todo.repository.TaskRepository
import dev.iaiabot.todo.repository.TaskRepositoryImpl
import dev.iaiabot.todo.repository.UserRepository
import dev.iaiabot.todo.repository.UserRepositoryImpl
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
