package dev.iaiabot.todo.login

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.google.common.truth.Truth.assertThat
import dev.iaiabot.usecase.CheckAlreadyLoggedInUseCase
import dev.iaiabot.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object LoginViewModelImplTest : Spek({
    lateinit var viewModel: LoginViewModel
    lateinit var loginUseCase: LoginUseCase
    lateinit var checkAlreadyLoggedInUseCase: CheckAlreadyLoggedInUseCase

    // TODO: TestRule化する
    beforeGroup {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    afterGroup {
        Dispatchers.resetMain()
    }
    beforeEachTest {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }
    afterEachTest { ArchTaskExecutor.getInstance().setDelegate(null) }

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
