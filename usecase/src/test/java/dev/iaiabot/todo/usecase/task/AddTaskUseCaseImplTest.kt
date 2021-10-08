package dev.iaiabot.todo.usecase.task

import dev.iaiabot.todo.repository.TaskRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object AddTaskUseCaseImplTest : Spek({
    lateinit var usecase: AddTaskUseCase
    lateinit var taskRepository: TaskRepository

    describe("#invoke") {
        beforeEachTest {
            taskRepository = mockk(relaxed = true)
            usecase = AddTaskUseCaseImpl(taskRepository)
        }

        it("タスクを追加している") {
            runBlockingTest {
                usecase()
            }
            coVerify(exactly = 1) { taskRepository.add(any()) }
        }
    }
})
