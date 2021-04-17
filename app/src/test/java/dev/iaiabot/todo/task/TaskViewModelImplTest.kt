package dev.iaiabot.todo.task

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.testrule.viewModelTestRule
import dev.iaiabot.usecase.*
import dev.iaiabot.usecase.task.AddTaskUseCase
import dev.iaiabot.usecase.task.GetAllCompletedTaskUseCase
import dev.iaiabot.usecase.task.GetAllIncompleteTaskUseCase
import dev.iaiabot.usecase.task.ToggleCompleteTaskUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object TaskViewModelImplTest : Spek({
    lateinit var viewModel: TaskViewModel
    lateinit var addTaskUseCase: AddTaskUseCase
    lateinit var getAllIncompleteTaskUseCase: GetAllIncompleteTaskUseCase
    lateinit var getAllCompletedTaskUseCase: GetAllCompletedTaskUseCase
    lateinit var toggleCompleteTaskUseCase: ToggleCompleteTaskUseCase

    val coroutineScope = viewModelTestRule()

    beforeEachTest {
        addTaskUseCase = mockk() {
            coEvery { this@mockk.invoke(any()) } returns true
        }
        getAllIncompleteTaskUseCase = mockk() {
            coEvery { this@mockk.invoke() } returns listOf(mockk())
        }
        getAllCompletedTaskUseCase = mockk() {
            coEvery { this@mockk.invoke() } returns listOf(mockk())
        }
        toggleCompleteTaskUseCase = mockk()
        viewModel = TaskViewModelImpl(
            addTaskUseCase,
            getAllIncompleteTaskUseCase,
            getAllCompletedTaskUseCase,
            toggleCompleteTaskUseCase
        )
    }

    describe("#init") {
        it("未完了と完了のタスクが両方設定されている") {
            viewModel.init()

            assertThat(viewModel.allTask.value).hasSize(2)
        }
    }

    describe("#addTask") {
        beforeEachTest {
            viewModel.newTaskTitle.value = "タスク"
        }

        it("タスク追加のユースケースを実行している") {
            viewModel.addTask()

            verify {
                addTaskUseCase.invoke(withArg {
                    assertThat(it).isEqualTo("タスク")
                })
            }
        }

        it("追加タスクの変数を空にしている") {
            assertThat(viewModel.newTaskTitle.value).isEqualTo("タスク")

            viewModel.addTask()

            assertThat(viewModel.newTaskTitle.value).isEqualTo("")
        }
    }

    describe("#onClickAddTask") {
        it("ableAddTaskをtoggleしている") {
            assertThat(viewModel.ableAddTask.value).isEqualTo(false)

            viewModel.onClickAddTask()

            assertThat(viewModel.ableAddTask.value).isEqualTo(true)
        }
    }
})
