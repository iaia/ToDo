package dev.iaiabot.usecase.task

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object GetAllIncompleteTaskUseCaseImplTest : Spek({
    lateinit var usecase: GetAllIncompleteTaskUseCase
    lateinit var taskRepository: TaskRepository

    describe("#invoke") {
        beforeEachTest {
            taskRepository = mockk(relaxed = true)
            usecase = GetAllIncompleteTaskUseCaseImpl(taskRepository)
        }

        context("repositoryから1個タスクが返る") {
            beforeEachTest {
                coEvery { taskRepository.allIncompleteTask() } returns listOf(mockk())
            }

            it("タスクが1個返る") {
                runBlockingTest {
                    val result = usecase.invoke()

                    assertThat(result.size).isEqualTo(1)
                }
            }
        }
    }
})
