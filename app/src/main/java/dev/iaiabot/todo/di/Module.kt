package dev.iaiabot.todo.di

import dev.iaiabot.todo.login.LoginViewModel
import dev.iaiabot.todo.login.LoginViewModelImpl
import dev.iaiabot.todo.task.TaskViewModel
import dev.iaiabot.todo.task.TaskViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val appModule = module {
        viewModel<LoginViewModel> { LoginViewModelImpl(get()) }
        viewModel<TaskViewModel> { TaskViewModelImpl(get(), get(), get()) }
    }
}
