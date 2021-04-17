package dev.iaiabot.repository

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.database.TaskDao
import dev.iaiabot.entity.Task
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object TaskRepositoryImplTest : Spek({
    lateinit var repository: TaskRepository
    lateinit var dao: TaskDao
    lateinit var userRepository: UserRepository
    val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

    describe("#add") {
        val task = mockk<Task>()

        beforeEachTest {
            dao = mockk() {
                every { add(any(), any()) } returns Unit
            }
            userRepository = mockk()
            repository = TaskRepositoryImpl(dao, userRepository, dispatcher)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userRepository.me()?.id } returns null
            }

            it("追加していない") {
                repository.add(task)

                verify(exactly = 0) { dao.add(any(), any()) }
            }
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userRepository.me()?.id } returns "user_id"
            }

            it("追加している") {
                repository.add(task)

                verify() { dao.add(any(), any()) }
            }
        }
    }

    describe("#saveCompletedState") {
        val taskId = "task_id"

        beforeEachTest {
            dao = mockk() {
                every { saveCompletedState(any(), any(), any()) } returns Unit
            }
            userRepository = mockk()
            repository = TaskRepositoryImpl(dao, userRepository, dispatcher)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userRepository.me()?.id } returns null
            }

            it("完了ステータスを更新していない") {
                repository.saveCompletedState(taskId, true)

                verify(exactly = 0) { dao.add(any(), any()) }
            }
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userRepository.me()?.id } returns "user_id"
            }

            it("完了ステータスを更新している") {
                repository.saveCompletedState(taskId, true)

                verify() { dao.saveCompletedState(any(), any(), true) }
            }
        }
    }

    describe("#allIncompleteTask") {
        val taskId = "task_id"

        beforeEachTest {
            dao = mockk()
            userRepository = mockk()
            repository = TaskRepositoryImpl(dao, userRepository, dispatcher)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userRepository.me()?.id } returns null
            }

            it("空が返る") {
                runBlockingTest(dispatcher) {
                    val result = repository.allIncompleteTask()

                    assertThat(result.size).isEqualTo(0)
                }
            }
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userRepository.me()?.id } returns "user_id"
            }

            context("1件登録済み") {
                beforeEachTest {
                    coEvery { dao.allIncompleteTask(any()) } returns listOf(mockk())
                }

                it("1件返る") {
                    runBlockingTest(dispatcher) {
                        val result = repository.allIncompleteTask()

                        assertThat(result.size).isEqualTo(1)
                    }
                }
            }
        }
    }

    describe("#allCompletedTask") {
        val taskId = "task_id"

        beforeEachTest {
            dao = mockk()
            userRepository = mockk()
            repository = TaskRepositoryImpl(dao, userRepository, dispatcher)
        }

        context("未ログイン") {
            beforeEachTest {
                every { userRepository.me()?.id } returns null
            }

            it("空が返る") {
                runBlockingTest(dispatcher) {
                    val result = repository.allCompletedTask()

                    assertThat(result.size).isEqualTo(0)
                }
            }
        }

        context("ログイン済み") {
            beforeEachTest {
                every { userRepository.me()?.id } returns "user_id"
            }

            context("1件登録済み") {
                beforeEachTest {
                    coEvery { dao.allCompletedTask(any()) } returns listOf(mockk())
                }

                it("1件返る") {
                    runBlockingTest(dispatcher) {
                        val result = repository.allCompletedTask()

                        assertThat(result.size).isEqualTo(1)
                    }
                }
            }
        }
    }
})
