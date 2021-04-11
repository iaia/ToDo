package dev.iaiabot.usecase.di

import dev.iaiabot.repository.di.Module.repositoryModule
import dev.iaiabot.usecase.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object Module {
    val useCaseModule = module {
        loadKoinModules(repositoryModule)

        single<GetAllIncompleteTaskUseCase> { GetAllIncompleteTaskUseCaseImpl(get()) }
        single<AddTaskUseCase> { AddTaskUseCaseImpl() }
        single<CompleteTaskUseCase> { CompleteTaskUseCaseImpl() }
        single<LoginUseCase> { LoginUseCaseImpl() }
        single<LogoutUseCase> { LogoutUseCaseImpl() }
        single<RestoreTaskUseCase> { RestoreTaskUseCaseImpl() }
    }
}
