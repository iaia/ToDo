package dev.iaiabot.repository.di

import dev.iaiabot.repository.TaskRepository
import dev.iaiabot.repository.TaskRepositoryImpl
import dev.iaiabot.repository.UserRepository
import dev.iaiabot.repository.UserRepositoryImpl
import org.koin.dsl.module

object Module {
    val repositoryModule = module {
        single<TaskRepository> { TaskRepositoryImpl() }
        single<UserRepository> { UserRepositoryImpl() }
    }
}
