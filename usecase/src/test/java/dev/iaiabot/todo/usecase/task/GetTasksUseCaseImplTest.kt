package dev.iaiabot.todo.usecase.task

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.model.Task
import dev.iaiabot.todo.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object GetTasksUseCaseImplTest : Spek({
    lateinit var usecase: GetTasksUseCase
    lateinit var taskRepository: TaskRepository
    val tasksFlow = flow<List<Task>> {
        emit(listOf(mockk()))
    }

    describe("#invoke") {
        beforeEachTest {
            taskRepository = mockk(relaxed = true)
            usecase = GetTasksUseCaseImpl(taskRepository)
        }

        context("repositoryから1個タスクが返る") {
            beforeEachTest {
                coEvery { taskRepository.getTasks(any()) } returns tasksFlow
            }

            it("タスクが1個返る") {
                runBlockingTest {
                    val result = usecase(true).toList()

                    assertThat(result.size).isEqualTo(1)
                }
            }
        }
    }
})
