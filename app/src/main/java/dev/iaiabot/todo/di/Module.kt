package dev.iaiabot.todo.di

import dev.iaiabot.todo.MainViewModel
import dev.iaiabot.todo.MainViewModelImpl
import dev.iaiabot.todo.feature.signup.SignUpViewModel
import dev.iaiabot.todo.feature.signup.SignUpViewModelImpl
import dev.iaiabot.todo.login.LoginViewModel
import dev.iaiabot.todo.login.LoginViewModelImpl
import dev.iaiabot.todo.task.TaskViewModel
import dev.iaiabot.todo.task.TaskViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val appModule = module {
        viewModel<MainViewModel> { MainViewModelImpl(get(), get()) }

        viewModel<LoginViewModel> { LoginViewModelImpl(get(), get()) }
        viewModel<SignUpViewModel> { SignUpViewModelImpl(get(), get()) }

        viewModel<TaskViewModel> { (showOnlyCompleted: Boolean) ->
            TaskViewModelImpl(
                showOnlyCompleted,
                get(),
                get(),
                get(),
                get()
            )
        }
    }
}
