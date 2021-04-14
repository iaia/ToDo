package dev.iaiabot.usecase

import dev.iaiabot.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object LoginUseCaseImplTest : Spek({
    lateinit var usecase: LoginUseCase
    lateinit var userRepository: UserRepository

    describe("#invoke") {
        beforeEachTest {
            userRepository = mockk(relaxed = true)
            usecase = LoginUseCaseImpl(userRepository)
        }

        context("emailがnull") {
            val email = null
            val password = "password"

            beforeEachTest {
                coEvery { userRepository.login(any(), any()) } returns Unit
            }

            it("ログインしない") {
                runBlockingTest {
                    usecase.invoke(email, password)

                    coVerify(exactly = 0) { userRepository.login(any(), any()) }
                }
            }
        }

        context("passwordがnull") {
            val email = "example@example.com"
            val password = null

            beforeEachTest {
                coEvery { userRepository.login(any(), any()) } returns Unit
            }

            it("ログインしない") {
                runBlockingTest {
                    usecase.invoke(email, password)

                    coVerify(exactly = 0) { userRepository.login(any(), any()) }
                }
            }
        }

        context("emailとpasswordがnull") {
            val email = null
            val password = null

            beforeEachTest {
                coEvery { userRepository.login(any(), any()) } returns Unit
            }

            it("ログインしない") {
                runBlockingTest {
                    usecase.invoke(email, password)

                    coVerify(exactly = 0) { userRepository.login(any(), any()) }
                }
            }
        }

        context("emailとpasswordが入ってる") {
            val email = "example@example.com"
            val password = "password"

            beforeEachTest {
                coEvery { userRepository.login(any(), any()) } returns Unit
            }

            it("ログインする") {
                runBlockingTest {
                    usecase.invoke(email, password)

                    coVerify { userRepository.login(any(), any()) }
                }
            }
        }
    }
})
