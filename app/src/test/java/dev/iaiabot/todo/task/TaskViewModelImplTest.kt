package dev.iaiabot.todo.task

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.model.Task
import dev.iaiabot.todo.testrule.viewModelTestRule
import dev.iaiabot.todo.usecase.*
import dev.iaiabot.todo.usecase.task.AddTaskUseCase
import dev.iaiabot.todo.usecase.task.ChangeTaskUseCase
import dev.iaiabot.todo.usecase.task.GetTasksUseCase
import dev.iaiabot.todo.usecase.task.ToggleCompleteTaskUseCase
import io.mockk.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object TaskViewModelImplTest : Spek({
    lateinit var viewModel: TaskViewModel
    lateinit var addTaskUseCase: AddTaskUseCase
    lateinit var getTasksUseCase: GetTasksUseCase
    lateinit var tasksFlow: Flow<List<Task>>
    lateinit var toggleCompleteTaskUseCase: ToggleCompleteTaskUseCase
    lateinit var changeTaskUseCase: ChangeTaskUseCase

    val coroutineScope = viewModelTestRule()

    beforeEachTest {
        addTaskUseCase = mockk() {
            coEvery { this@mockk() } returns Unit
        }
        tasksFlow = flow {
            emit(listOf(mockk()))
        }
        getTasksUseCase = mockk() {
            coEvery { this@mockk(any()) } returns tasksFlow
        }
        toggleCompleteTaskUseCase = mockk()
        changeTaskUseCase = mockk()
        viewModel = TaskViewModelImpl(
            true,
            getTasksUseCase,
            addTaskUseCase,
            toggleCompleteTaskUseCase,
            changeTaskUseCase,
        )
    }

    describe("#tasks") {
        it("1個タスクを持っている") {
            viewModel.tasks.observeForever { }

            assertThat(viewModel.tasks.value?.size).isEqualTo(1)
        }
    }

    describe("#onClickAddTask") {
        it("タスク追加のユースケースを実行している") {
            viewModel.onClickAddTask()

            coVerify { addTaskUseCase() }
        }
    }

    describe("#toggleComplete") {
        val task = mockk<Task>()

        it("タスク追加のユースケースを実行している") {
            viewModel.toggleComplete(task)

            coVerify { toggleCompleteTaskUseCase(task) }
        }
    }

    describe("#onChangeTask") {
        val task = mockk<Task>() {
            every { title } returns "TODO"
        }

        it("タスク追加のユースケースを実行している") {
            viewModel.onChangeTask(task, "NEW_TODO")

            coVerify { changeTaskUseCase(task, "NEW_TODO") }
        }
    }
})
