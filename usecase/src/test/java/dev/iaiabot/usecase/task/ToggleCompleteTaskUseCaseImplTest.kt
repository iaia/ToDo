package dev.iaiabot.usecase.task

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal class ToggleCompleteTaskUseCaseImplTest : Spek({
    lateinit var usecase: ToggleCompleteTaskUseCase
    lateinit var taskRepository: TaskRepository

    describe("#invoke") {
        beforeEachTest {
            taskRepository = mockk(relaxed = true)
            usecase = ToggleCompleteTaskUseCaseImpl(taskRepository)
        }

        context("taskが渡される") {
            val task = mockk<Task>() {
                every { id } returns "task_id"
                every { completed } returns false
            }

            beforeEachTest {
                every { taskRepository.saveCompletedState(any(), any()) } returns Unit
            }

            it("渡したtaskのidで保存している") {
                usecase(task)

                verify {
                    taskRepository.saveCompletedState(
                        withArg {
                            assertThat(it).isEqualTo("task_id")
                        },
                        any()
                    )
                }
            }

            it("渡したtaskのcompletedとは反対で保存している") {
                usecase(task)

                verify {
                    taskRepository.saveCompletedState(
                        any(),
                        withArg {
                            assertThat(it).isEqualTo(true)
                        },
                    )
                }
            }
        }
    }
})
