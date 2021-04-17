package dev.iaiabot.usecase.task

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object GetAllCompletedTaskUseCaseImplTest : Spek({
    lateinit var usecase: GetAllCompletedTaskUseCase
    lateinit var taskRepository: TaskRepository

    describe("#invoke") {
        beforeEachTest {
            taskRepository = mockk(relaxed = true)
            usecase = GetAllCompletedTaskUseCaseImpl(taskRepository)
        }

        context("repositoryから1個タスクが返る") {
            beforeEachTest {
                coEvery { taskRepository.allCompletedTask() } returns listOf(mockk())
            }

            it("タスクが1個返る") {
                runBlockingTest {
                    val result = usecase()

                    assertThat(result.size).isEqualTo(1)
                }
            }
        }
    }
})
