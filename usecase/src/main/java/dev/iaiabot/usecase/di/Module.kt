package dev.iaiabot.usecase.di

import dev.iaiabot.repository.di.Module.repositoryModule
import dev.iaiabot.usecase.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object Module {
    val useCaseModule = module {
        loadKoinModules(repositoryModule)

        single<GetAllIncompleteTaskUseCase> { GetAllIncompleteTaskUseCaseImpl(get()) }
        single<RefreshAllTasks> { RefreshAllTasksImpl(get()) }
        single<AddTaskUseCase> { AddTaskUseCaseImpl(get()) }
        single<CompleteTaskUseCase> { CompleteTaskUseCaseImpl(get()) }
        single<LoginUseCase> { LoginUseCaseImpl(get()) }
        single<CheckAlreadyLoggedInUseCase> { CheckAlreadyLoggedInUseCaseImpl(get()) }
        single<LogoutUseCase> { LogoutUseCaseImpl(get()) }
        single<RestoreTaskUseCase> { RestoreTaskUseCaseImpl() }
    }
}
