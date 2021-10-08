package dev.iaiabot.todo

import com.google.common.truth.Truth.*
import dev.iaiabot.todo.testrule.viewModelTestRule
import dev.iaiabot.todo.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.todo.usecase.user.LogoutUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object MainViewModelImplTest : Spek({
    lateinit var viewModel: MainViewModel
    lateinit var logoutUseCase: LogoutUseCase
    lateinit var checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase

    val coroutineScope = viewModelTestRule()

    describe("#loggedIn") {
        lateinit var loggedInFlow: Flow<Boolean>

        beforeGroup {
            logoutUseCase = mockk()
            checkAlreadyLoggedInUseCase = mockk()
        }

        context("すでにログインしている場合") {
            beforeEachTest {
                loggedInFlow = flow<Boolean> {
                    emit(true)
                }
                every { checkAlreadyLoggedInUseCase() } returns loggedInFlow
                viewModel = MainViewModelImpl(checkAlreadyLoggedInUseCase, logoutUseCase)
            }

            it("loggedInがtrueになっている") {
                viewModel.loggedIn.observeForever { }
                coroutineScope.runBlockingTest {
                    assertThat(viewModel.loggedIn.value).isTrue()
                }
            }
        }

        context("未ログインの場合") {
            beforeEachTest {
                loggedInFlow = flow<Boolean> {
                    emit(false)
                }
                coEvery { checkAlreadyLoggedInUseCase() } returns loggedInFlow
                viewModel = MainViewModelImpl(checkAlreadyLoggedInUseCase, logoutUseCase)
            }

            it("loggedInがfalseになっている") {
                viewModel.loggedIn.observeForever { }
                coroutineScope.runBlockingTest {
                    assertThat(viewModel.loggedIn.value).isFalse()
                }
            }
        }
    }

    describe("#onClickLogout") {
        beforeEachTest {
            logoutUseCase = mockk() {
                coEvery { this@mockk() } returns Unit
            }
            val loggedInFlow = flow<Boolean> {
                emit(true)
            }
            checkAlreadyLoggedInUseCase = mockk() {
                coEvery { this@mockk() } returns loggedInFlow
            }
            viewModel = MainViewModelImpl(checkAlreadyLoggedInUseCase, logoutUseCase)
        }

        it("ログアウトusecaseを実行している") {
            coroutineScope.runBlockingTest {
                viewModel.onClickLogout()

                coVerify { logoutUseCase() }
            }
        }
    }
})
