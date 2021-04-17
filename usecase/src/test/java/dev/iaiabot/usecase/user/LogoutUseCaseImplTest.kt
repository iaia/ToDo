package dev.iaiabot.usecase.user

import dev.iaiabot.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object LogoutUseCaseImplTest : Spek({
    lateinit var usecase: LogoutUseCase
    lateinit var userRepository: UserRepository

    describe("#invoke") {
        beforeEachTest {
            userRepository = mockk(relaxed = true)
            coEvery { userRepository.logout() } returns Unit
            usecase = LogoutUseCaseImpl(userRepository)
        }

        it("ログアウトしてる") {
            runBlockingTest {
                usecase.invoke()

                coVerify { userRepository.logout() }
            }
        }
    }
})
