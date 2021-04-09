package dev.iaiabot.usecase.di

import dev.iaiabot.usecase.*
import org.koin.dsl.module

object Module {
    val useCaseModule = module {
        single<AddTaskUseCase> { AddTaskUseCaseImpl() }
        single<CompleteTaskUseCase> { CompleteTaskUseCaseImpl() }
        single<LoginUseCase> { LoginUseCaseImpl() }
        single<LogoutUseCase> { LogoutUseCaseImpl() }
        single<RestoreTaskUseCase> { RestoreTaskUseCaseImpl() }

    }
}
