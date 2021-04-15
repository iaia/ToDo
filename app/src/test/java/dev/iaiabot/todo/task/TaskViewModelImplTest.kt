package dev.iaiabot.todo.task

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.testrule.viewModelTestRule
import dev.iaiabot.usecase.*
import io.mockk.coEvery
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object TaskViewModelImplTest : Spek({
    lateinit var viewModel: TaskViewModel
    lateinit var addTaskUseCase: AddTaskUseCase
    lateinit var getAllIncompleteTaskUseCase: GetAllIncompleteTaskUseCase
    lateinit var getAllCompletedTaskUseCase: GetAllCompletedTaskUseCase
    lateinit var completeTaskUseCase: CompleteTaskUseCase

    val coroutineScope = viewModelTestRule()

    describe("#init") {
        beforeEachTest {
            addTaskUseCase = mockk()
            getAllIncompleteTaskUseCase = mockk() {
                coEvery { this@mockk.invoke() } returns listOf(mockk())
            }
            getAllCompletedTaskUseCase = mockk() {
                coEvery { this@mockk.invoke() } returns listOf(mockk())
            }
            completeTaskUseCase = mockk()
            viewModel = TaskViewModelImpl(
                addTaskUseCase,
                getAllIncompleteTaskUseCase,
                getAllCompletedTaskUseCase,
                completeTaskUseCase
            )
        }

        it("未完了と完了のタスクが両方設定されている") {
            viewModel.init()

            assertThat(viewModel.allTask.value).hasSize(1)
            assertThat(viewModel.allCompletedTask.value).hasSize(1)
        }
    }
})
