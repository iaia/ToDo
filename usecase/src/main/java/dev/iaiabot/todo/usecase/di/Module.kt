package dev.iaiabot.todo.usecase.di

import dev.iaiabot.todo.repository.di.Module.repositoryModule
import dev.iaiabot.todo.usecase.task.*
import dev.iaiabot.todo.usecase.user.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object Module {
    val useCaseModule = module {
        loadKoinModules(repositoryModule)

        single<LoginUseCase> { LoginUseCaseImpl(get()) }
        single<CheckAlreadyLoggedInUseCase> { CheckAlreadyLoggedInUseCaseImpl(get()) }
        single<LogoutUseCase> { LogoutUseCaseImpl(get()) }
        factory<SignUpUseCase> { SignUpUseCaseImpl(get()) }

        factory<AddTaskUseCase> { AddTaskUseCaseImpl(get()) }
        factory<ToggleCompleteTaskUseCase> { ToggleCompleteTaskUseCaseImpl(get()) }
        factory<GetTasksUseCase> { GetTasksUseCaseImpl(get()) }
        factory<ChangeTaskUseCase> { ChangeTaskUseCaseImpl(get()) }
        factory<DeleteTaskUseCase> { DeleteTaskUseCaseImpl(get()) }
    }
}
