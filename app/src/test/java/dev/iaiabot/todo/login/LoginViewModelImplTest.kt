package dev.iaiabot.todo.login

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.testrule.viewModelTestRule
import dev.iaiabot.usecase.user.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.user.LoginUseCase
import io.mockk.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object LoginViewModelImplTest : Spek({
    lateinit var viewModel: LoginViewModel
    lateinit var loginUseCase: LoginUseCase
    lateinit var loginFlow: Flow<Unit>
    lateinit var checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase
    lateinit var loggedInFlow: Flow<Boolean>

    val coroutineScope = viewModelTestRule()

    describe("#onClickLogin") {
        beforeEachTest {
            loginFlow = flow { }
            loginUseCase = mockk() {
                coEvery { this@mockk(any(), any()) } returns loginFlow
            }
            loggedInFlow = flow {
            }
            checkAlreadyLoggedInUseCase = mockk() {
                coEvery { this@mockk() } returns loggedInFlow
            }
            viewModel = LoginViewModelImpl(checkAlreadyLoggedInUseCase, loginUseCase)
        }

        it("ログインusecaseを実行している") {
            coroutineScope.runBlockingTest {
                viewModel.onClickLogin()

                coVerify { loginUseCase(any(), any()) }
            }
        }

        it("nowLoginがfalse->true->falseになっている") {
            assertThat(viewModel.nowLogin.value).isEqualTo(false)
            coroutineScope.pauseDispatcher()
            viewModel.onClickLogin()
            assertThat(viewModel.nowLogin.value).isEqualTo(true)
            coroutineScope.resumeDispatcher()
            assertThat(viewModel.nowLogin.value).isEqualTo(false)
        }
    }
})
