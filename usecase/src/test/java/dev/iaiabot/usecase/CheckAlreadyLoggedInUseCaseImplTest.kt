package dev.iaiabot.usecase

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object CheckAlreadyLoggedInUseCaseImplTest : Spek({
    lateinit var usecase: CheckAlreadyLoggedInUseCase
    lateinit var userRepository: UserRepository

    describe("#invoke") {
        beforeEachTest {
            userRepository = mockk(relaxed = true)
            usecase = CheckAlreadyLoggedInUseCaseImpl(userRepository)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userRepository.me() } returns null
            }

            it("falseが返る") {
                runBlockingTest {
                    val result = usecase.invoke()

                    assertThat(result).isEqualTo(false)
                }
            }
        }

        context("すでにログイン済み") {
            beforeEachTest {
                every { userRepository.me() } returns mockk()
            }

            it("trueが返る") {
                runBlockingTest {
                    val result = usecase.invoke()

                    assertThat(result).isEqualTo(true)
                }
            }
        }
    }
})
