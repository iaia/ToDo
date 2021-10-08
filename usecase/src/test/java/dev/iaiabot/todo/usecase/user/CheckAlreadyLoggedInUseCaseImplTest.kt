package dev.iaiabot.todo.usecase.user

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object CheckAlreadyLoggedInUseCaseImplTest : Spek({
    lateinit var usecase: CheckAlreadyLoggedInUseCase
    lateinit var userRepository: UserRepository
    lateinit var alreadyLoggedIn: Flow<Boolean>

    describe("#invoke") {
        beforeEachTest {
            userRepository = mockk(relaxed = true)
            usecase = CheckAlreadyLoggedInUseCaseImpl(userRepository)
        }

        context("未ログイン") {
            beforeEachTest {
                alreadyLoggedIn = flow { emit(false) }
                every { userRepository.alreadyLoggedIn } returns alreadyLoggedIn
            }

            it("falseが返る") {
                runBlockingTest {
                    val result = usecase().single()

                    assertThat(result).isEqualTo(false)
                }
            }
        }

        context("すでにログイン済み") {
            beforeEachTest {
                alreadyLoggedIn = flow { emit(true) }
                every { userRepository.alreadyLoggedIn } returns alreadyLoggedIn
            }

            it("trueが返る") {
                runBlockingTest {
                    val result = usecase().single()

                    assertThat(result).isEqualTo(true)
                }
            }
        }
    }
})
