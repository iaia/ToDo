package dev.iaiabot.todo.login

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.testrule.viewModelTestRule
import dev.iaiabot.usecase.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object LoginViewModelImplTest : Spek({
    lateinit var viewModel: LoginViewModel
    lateinit var loginUseCase: LoginUseCase
    lateinit var checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase

    viewModelTestRule()

    describe("#onResume") {
        beforeEachTest {
            loginUseCase = mockk()
            checkAlreadyLoggedInUseCase = mockk()
            viewModel = LoginViewModelImpl(loginUseCase, checkAlreadyLoggedInUseCase)
        }

        context("すでにログインしている場合") {
            beforeEachTest {
                coEvery { checkAlreadyLoggedInUseCase.invoke() } returns true
            }

            it("routerActionがGoToTasksになっている") {
                viewModel.onResume()

                assertThat(viewModel.routerAction.value).isEqualTo(Action.GoToTasks)
            }
        }
    }
})
