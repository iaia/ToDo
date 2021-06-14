package dev.iaiabot.usecase.di

import dev.iaiabot.repository.di.Module.repositoryModule
import dev.iaiabot.usecase.task.*
import dev.iaiabot.usecase.user.*
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
        factory<GetAllTaskUseCase> { GetAllTaskUseCaseImpl(get()) }
        factory<ChangeTaskUseCase> { ChangeTaskUseCaseImpl(get()) }
    }
}
