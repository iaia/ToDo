package dev.iaiabot.todo.repository

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.todo.database.TaskDataSource
import dev.iaiabot.todo.model.Task
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertThrows
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object TaskRepositoryImplTest : Spek({
    lateinit var repository: TaskRepository
    lateinit var dataSource: TaskDataSource
    lateinit var userRepository: UserRepository
    val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

    describe("#add") {
        val task = mockk<Task>()

        beforeEachTest {
            dataSource = mockk() {
                every { add(any(), any()) } returns Unit
            }
            userRepository = mockk()
            repository = TaskRepositoryImpl(dataSource, userRepository, dispatcher)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userRepository.me()?.id } returns null
            }

            it("追加していない") {
                runBlockingTest {
                    repository.add(task)
                }

                verify(exactly = 0) { dataSource.add(any(), any()) }
            }
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userRepository.me()?.id } returns "user_id"
            }

            it("追加している") {
                runBlockingTest {
                    repository.add(task)
                }

                verify() { dataSource.add(any(), any()) }
            }
        }
    }

    describe("#saveCompletedState") {
        val taskId = "task_id"

        beforeEachTest {
            dataSource = mockk() {
                every { saveCompletedState(any(), any(), any()) } returns Unit
            }
            userRepository = mockk()
            repository = TaskRepositoryImpl(dataSource, userRepository, dispatcher)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userRepository.me()?.id } returns null
            }

            it("完了ステータスを更新していない") {
                repository.saveCompletedState(taskId, true)

                verify(exactly = 0) { dataSource.add(any(), any()) }
            }
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userRepository.me()?.id } returns "user_id"
            }

            it("完了ステータスを更新している") {
                repository.saveCompletedState(taskId, true)

                verify() { dataSource.saveCompletedState(any(), any(), true) }
            }
        }
    }

    describe("#getTasks") {
        val taskId = "task_id"

        beforeEachTest {
            dataSource = mockk()
            userRepository = mockk()
            repository = TaskRepositoryImpl(dataSource, userRepository, dispatcher)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userRepository.me()?.id } returns null
            }

            it("例外が返る") {
                runBlockingTest(dispatcher) {
                    assertThrows(Exception::class.java) {
                        repository.getTasks(true)
                    }
                }
            }
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userRepository.me()?.id } returns "user_id"
            }

            context("1件登録済み") {
                val tasksFlow = flow<List<Task>> {
                    emit(listOf(mockk()))
                }

                beforeEachTest {
                    coEvery { dataSource.getTasks(any(), any()) } returns tasksFlow
                }

                it("1件返る") {
                    runBlockingTest(dispatcher) {
                        val result = repository.getTasks(true).toList()

                        assertThat(result.size).isEqualTo(1)
                    }
                }
            }
        }
    }
})
