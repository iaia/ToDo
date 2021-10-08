package dev.iaiabot.todo.repository

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.auth.UserAuth
import dev.iaiabot.todo.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object UserRepositoryImplTest : Spek({
    lateinit var repository: UserRepository
    lateinit var userAuth: UserAuth
    val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

    describe("#me") {
        beforeEachTest {
            userAuth = mockk() {
                every { alreadyLoggedIn } returns mockk()
            }
            repository = UserRepositoryImpl(userAuth, dispatcher)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userAuth.me } returns null
            }

            it("nullが返る") {
                val result = repository.me()

                assertThat(result).isNull()
            }
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userAuth.me } returns mockk<User>()
            }

            it("userインスタンスが返る") {
                val result = repository.me()

                assertThat(result).isInstanceOf(User::class.java)
            }
        }
    }

    describe("#login") {
        val email = "example@example.com"
        val password = "password"

        beforeEachTest {
            userAuth = mockk() {
                every { alreadyLoggedIn } returns mockk()
                coEvery { login(any(), any()) } returns mockk()
            }
            repository = UserRepositoryImpl(userAuth, dispatcher)
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userAuth.me } returns mockk<User>()
            }

            it("再度ログインしない") {
                runBlockingTest(dispatcher) {
                    try {
                        repository.login(email, password)
                    } catch (e: Exception) {
                    }

                    coVerify(exactly = 0) { userAuth.login(any(), any()) }
                }
            }
        }

        context("未ログイン") {
            beforeEachTest {
                every { userAuth.me } returns null
            }

            it("ログインしている") {
                runBlockingTest(dispatcher) {
                    repository.login(email, password)

                    coVerify {
                        userAuth.login(
                            withArg { assertThat(it).isEqualTo("example@example.com") },
                            withArg { assertThat(it).isEqualTo("password") }
                        )
                    }
                }
            }
        }
    }

    describe("#logout") {
        beforeEachTest {
            userAuth = mockk {
                every { alreadyLoggedIn } returns mockk()
                coEvery { logout() } returns Unit
            }
            repository = UserRepositoryImpl(userAuth, dispatcher)
        }

        it("ログアウトしている") {
            runBlockingTest {
                repository.logout()

                coVerify { userAuth.logout() }
            }
        }
    }

    describe("#signUp") {
        beforeEachTest {
            userAuth = mockk {
                every { alreadyLoggedIn } returns mockk()
                coEvery { signUp(any(), any()) } returns Unit
            }
            repository = UserRepositoryImpl(userAuth, dispatcher)
        }

        it("サインアップしている") {
            runBlockingTest {
                repository.signUp("EMAIL", "PASSWORD")

                coVerify { userAuth.signUp("EMAIL", "PASSWORD") }
            }
        }
    }
})
