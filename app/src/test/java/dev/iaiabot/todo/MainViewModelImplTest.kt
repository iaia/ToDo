package dev.iaiabot.todo

import com.google.common.truth.Truth.*
import dev.iaiabot.todo.testrule.viewModelTestRule
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LogoutUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object MainViewModelImplTest : Spek({
    lateinit var viewModel: MainViewModel
    lateinit var logoutUseCase: LogoutUseCase
    lateinit var checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase

    val coroutineScope = viewModelTestRule()

    describe("#onResume") {
        beforeEachTest {
            logoutUseCase = mockk()
            checkAlreadyLoggedInUseCase = mockk()
            viewModel = MainViewModelImpl(checkAlreadyLoggedInUseCase, logoutUseCase)
        }

        context("すでにログインしている場合") {
            beforeEachTest {
                coEvery { checkAlreadyLoggedInUseCase.invoke() } returns true
                assertThat(viewModel.loggedIn.value).isFalse()
            }

            it("loggedInがtrueになっている") {
                coroutineScope.runBlockingTest {
                    viewModel.onResume()

                    assertThat(viewModel.loggedIn.value).isTrue()
                }
            }
        }

        context("未ログインの場合") {
            beforeEachTest {
                coEvery { checkAlreadyLoggedInUseCase.invoke() } returns false
                assertThat(viewModel.loggedIn.value).isFalse()
            }

            it("loggedInがfalseになっている") {
                coroutineScope.runBlockingTest {
                    viewModel.onResume()

                    assertThat(viewModel.loggedIn.value).isFalse()
                }
            }
        }
    }

    describe("#onClickLogout") {
        beforeEachTest {
            logoutUseCase = mockk() {
                coEvery { this@mockk.invoke() } returns Unit
            }
            checkAlreadyLoggedInUseCase = mockk() {
                coEvery { this@mockk.invoke() } returns true
            }
            viewModel = MainViewModelImpl(checkAlreadyLoggedInUseCase, logoutUseCase)
        }

        it("ログアウトusecaseを実行している") {
            coroutineScope.runBlockingTest {
                viewModel.onClickLogout()

                coVerify { logoutUseCase.invoke() }
            }
        }

        it("routerActionがGoToTasksになっている") {
            coroutineScope.runBlockingTest {
                viewModel.onClickLogout()

                assertThat(viewModel.routerAction.value).isEqualTo(Action.Finish)
            }
        }
    }
})
