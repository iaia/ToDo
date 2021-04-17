package dev.iaiabot.usecase.task

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.entity.Task
import dev.iaiabot.repository.TaskRepository
import io.mockk.*
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

        context("titleがnull") {
            val title = null

            it("タスクを追加していない") {
                usecase(title)

                verify(exactly = 0) { taskRepository.add(any()) }
            }
        }

        context("titleが空") {
            val title = ""

            it("タスクを追加していない") {
                usecase(title)
                verify(exactly = 0) { taskRepository.add(any()) }
            }
        }

        context("titleがなにか入っている") {
            val title = "タイトル"
            lateinit var taskSlot: CapturingSlot<Task>

            beforeEachTest {
                taskSlot = slot()
                every { taskRepository.add(capture(taskSlot)) } returns Unit
            }

            it("タスクを追加している") {
                usecase(title)

                verify { taskRepository.add(any()) }
            }

            it("追加するTaskのtitleが引数と等しい") {
                usecase(title)

                assertThat(taskSlot.captured.title).isEqualTo("タイトル")
            }
        }
    }
})
