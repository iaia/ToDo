package dev.iaiabot.todo.login

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.testrule.viewModelTestRule
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LoginUseCase
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object LoginViewModelImplTest : Spek({
    lateinit var viewModel: LoginViewModel
    lateinit var loginUseCase: LoginUseCase
    lateinit var checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase

    val coroutineScope = viewModelTestRule()

    describe("#onResume") {
        beforeEachTest {
            loginUseCase = mockk()
            checkAlreadyLoggedInUseCase = mockk()
            viewModel = LoginViewModelImpl(loginUseCase, checkAlreadyLoggedInUseCase)
        }

        context("すでにログインしている場合") {
            beforeEachTest {
                coEvery { checkAlreadyLoggedInUseCase() } returns true
                assertThat(viewModel.routerAction.value).isNull()
            }

            it("routerActionがGoToTasksになっている") {
                coroutineScope.runBlockingTest {
                    viewModel.onResume()

                    assertThat(viewModel.routerAction.value).isEqualTo(Action.GoToTasks)
                }
            }
        }

        context("未ログインの場合") {
            beforeEachTest {
                coEvery { loginUseCase(any(), any()) } returns Unit
                coEvery { checkAlreadyLoggedInUseCase() } returns false
                assertThat(viewModel.routerAction.value).isNull()
            }

            it("routerActionがnullになっている") {
                coroutineScope.runBlockingTest {
                    viewModel.onResume()

                    assertThat(viewModel.routerAction.value).isNull()
                }
            }
        }
    }

    describe("#onClickLogin") {
        beforeEachTest {
            loginUseCase = mockk() {
                coEvery { this@mockk(any(), any()) } returns Unit
            }
            checkAlreadyLoggedInUseCase = mockk() {
                coEvery { this@mockk() } returns true
            }
            viewModel = LoginViewModelImpl(loginUseCase, checkAlreadyLoggedInUseCase)
        }

        it("ログインusecaseを実行している") {
            coroutineScope.runBlockingTest {
                viewModel.onClickLogin()

                coVerify { loginUseCase(any(), any()) }
            }
        }

        it("routerActionがGoToTasksになっている") {
            coroutineScope.runBlockingTest {
                viewModel.onClickLogin()

                assertThat(viewModel.routerAction.value).isEqualTo(Action.GoToTasks)
            }
        }
    }
})
