package dev.iaiabot.todo.usecase.user

import dev.iaiabot.todo.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertThrows
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object LoginUseCaseImplTest : Spek({
    lateinit var usecase: LoginUseCase
    lateinit var userRepository: UserRepository
    lateinit var loginFlow: Flow<Unit>

    describe("#invoke") {
        beforeEachTest {
            userRepository = mockk(relaxed = true)
            usecase = LoginUseCaseImpl(userRepository)
        }

        context("emailがnull") {
            val email = null
            val password = "password"

            beforeEachTest {
                loginFlow = flow { }
                coEvery { userRepository.login(any(), any()) } returns loginFlow
            }

            it("ログインしない") {
                assertThrows(Exception::class.java) {
                    runBlockingTest {
                        usecase(email, password)
                    }
                }

                coVerify(exactly = 0) { userRepository.login(any(), any()) }
            }
        }

        context("passwordがnull") {
            val email = "example@example.com"
            val password = null

            beforeEachTest {
                loginFlow = flow { }
                coEvery { userRepository.login(any(), any()) } returns loginFlow
            }

            it("ログインしない") {
                assertThrows(Exception::class.java) {
                    runBlockingTest {
                        usecase(email, password)
                    }
                }

                coVerify(exactly = 0) { userRepository.login(any(), any()) }
            }
        }

        context("emailとpasswordがnull") {
            val email = null
            val password = null

            beforeEachTest {
                loginFlow = flow { }
                coEvery { userRepository.login(any(), any()) } returns loginFlow
            }

            it("ログインしない") {
                assertThrows(Exception::class.java) {

                    runBlockingTest {
                        usecase(email, password)
                    }
                }

                coVerify(exactly = 0) { userRepository.login(any(), any()) }
            }
        }

        context("emailとpasswordが入ってる") {
            val email = "example@example.com"
            val password = "password"

            beforeEachTest {
                loginFlow = flow { }
                coEvery { userRepository.login(any(), any()) } returns loginFlow
            }

            it("ログインする") {
                runBlockingTest {
                    usecase(email, password)

                    coVerify { userRepository.login(any(), any()) }
                }
            }
        }
    }
})
